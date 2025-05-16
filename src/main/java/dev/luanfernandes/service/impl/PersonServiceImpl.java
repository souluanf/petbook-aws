package dev.luanfernandes.service.impl;

import static dev.luanfernandes.domain.util.PhotoKeyGenerator.generate;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import dev.luanfernandes.domain.entity.Animal;
import dev.luanfernandes.domain.entity.Person;
import dev.luanfernandes.domain.enums.PersonEventType;
import dev.luanfernandes.domain.exception.AlreadyExistsException;
import dev.luanfernandes.domain.exception.InvalidFileTypeException;
import dev.luanfernandes.domain.exception.NotFoundException;
import dev.luanfernandes.domain.exception.PersonHasAssociatedAnimalsException;
import dev.luanfernandes.domain.mapper.PersonMapper;
import dev.luanfernandes.domain.request.PersonCreateRequest;
import dev.luanfernandes.domain.request.PersonUpdateRequest;
import dev.luanfernandes.domain.response.PageResponse;
import dev.luanfernandes.domain.response.PaginationCursor;
import dev.luanfernandes.domain.response.PersonResponse;
import dev.luanfernandes.domain.response.PhotoDownload;
import dev.luanfernandes.service.PersonService;
import dev.luanfernandes.service.SnsService;
import dev.luanfernandes.webclient.aws.AwsS3Client;
import dev.luanfernandes.webclient.opencep.OpenCepClient;
import dev.luanfernandes.webclient.opencep.response.OpenCepResponse;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final DynamoDbTemplate template;
    private final PersonMapper mapper;
    private final AwsS3Client awsS3Client;
    private final SnsService snsService;
    private final OpenCepClient brasilApiClient;

    @Override
    public PersonResponse create(PersonCreateRequest request) {
        var query = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(
                        Key.builder().partitionValue(request.email()).build()))
                .build();
        boolean exists = template.query(query, Person.class, "emailIndex").stream()
                .flatMap(p -> p.items().stream())
                .findFirst()
                .isPresent();

        if (exists) {
            throw new AlreadyExistsException("Email already registered: " + request.email());
        }

        OpenCepResponse openCepResponse = brasilApiClient.byPostalCode(request.zipCode());
        if (openCepResponse == null) {
            throw new NotFoundException("ZipCode code not found: " + request.zipCode());
        }

        Person person = mapper.toEntity(request);
        mapper.updateWithAddress(openCepResponse, person);
        person.setAddressComplement(request.addressComplement());
        person.setAddressBuildingNumber(request.addressBuildingNumber());

        log.info("Creating person with id: {}", person);

        template.save(person);
        snsService.publishPersonEvent(PersonEventType.ACCOUNT_CREATED, person);
        return mapper.toResponse(person);
    }

    @Override
    public void uploadPhoto(String id, MultipartFile file) {
        String contentType = file.getContentType();
        if (!IMAGE_JPEG_VALUE.equals(contentType) && !IMAGE_PNG_VALUE.equals(contentType)) {
            throw new InvalidFileTypeException("Tipo de arquivo não suportado. Apenas JPEG e PNG são permitidos.");
        }
        Person person = Optional.ofNullable(
                        template.load(Key.builder().partitionValue(id).build(), Person.class))
                .orElseThrow(() -> new NotFoundException("Person not found with id: " + id));

        String extension = IMAGE_PNG_VALUE.equals(contentType) ? "png" : "jpg";
        String key = generate("animals", id, extension);

        awsS3Client.uploadFile(key, file);

        person.setPhotoKey(key);
        person.setUpdatedAt(Instant.now());
        snsService.publishPersonEvent(PersonEventType.PHOTO_UPLOADED, person);
        template.update(person);
    }

    @Override
    public PhotoDownload downloadPhoto(String id) {
        Person person = Optional.ofNullable(
                        template.load(Key.builder().partitionValue(id).build(), Person.class))
                .orElseThrow(() -> new NotFoundException("Person not found with id: " + id));
        String key = person.getPhotoKey();
        if (key == null || key.isEmpty()) {
            throw new NotFoundException("Photo not uploaded");
        }
        byte[] bytes = awsS3Client.downloadFile(key);
        MediaType mediaType = key.endsWith(".png") ? IMAGE_PNG : IMAGE_JPEG;
        return new PhotoDownload(bytes, mediaType);
    }

    @Override
    public PersonResponse findById(String id) {
        Person person = Optional.ofNullable(
                        template.load(Key.builder().partitionValue(id).build(), Person.class))
                .orElseThrow(() -> new NotFoundException("Person not found with id: " + id));
        return mapper.toResponse(person);
    }

    public PageResponse<PersonResponse> findAll(int pageSize, String paginationKey) {
        Map<String, AttributeValue> exclusiveStartKey = Optional.ofNullable(paginationKey)
                .filter(id -> !id.isBlank())
                .map(id -> Map.of("id", AttributeValue.builder().s(id).build()))
                .orElse(null);

        Page<Person> page = template
                .scan(
                        ScanEnhancedRequest.builder()
                                .limit(pageSize)
                                .exclusiveStartKey(exclusiveStartKey)
                                .build(),
                        Person.class)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nenhum resultado retornado do scan."));

        String lastEvaluatedId = Optional.ofNullable(page.lastEvaluatedKey())
                .map(m -> m.get("id"))
                .map(AttributeValue::s)
                .orElse(null);

        PaginationCursor lastEvaluatedKey = (lastEvaluatedId == null)
                ? null
                : PaginationCursor.builder().id(lastEvaluatedId).build();

        return PageResponse.<PersonResponse>builder()
                .items(page.items().stream().map(mapper::toResponse).toList())
                .lastEvaluatedKey(lastEvaluatedKey)
                .build();
    }

    @Override
    public PersonResponse update(String id, PersonUpdateRequest request) {
        Person person = Optional.ofNullable(
                        template.load(Key.builder().partitionValue(id).build(), Person.class))
                .orElseThrow(() -> new NotFoundException("Person not found with id: " + id));
        if (request.email() != null && !request.email().equalsIgnoreCase(person.getEmail())) {
            var query = QueryEnhancedRequest.builder()
                    .queryConditional(QueryConditional.keyEqualTo(
                            Key.builder().partitionValue(request.email()).build()))
                    .build();
            if (!person.getEmail().equals(request.email())) {
                boolean exists = template.query(query, Person.class, "emailIndex").stream()
                        .flatMap(p -> p.items().stream())
                        .findFirst()
                        .isPresent();
                if (exists) {
                    throw new AlreadyExistsException("Email already registered: " + request.email());
                }
            }
        }
        mapper.updateFromRequest(request, person);
        person.setUpdatedAt(Instant.now());
        template.update(person);
        snsService.publishPersonEvent(PersonEventType.ACCOUNT_UPDATED, person);
        return mapper.toResponse(person);
    }

    @Override
    public void delete(String id) {
        Person person = Optional.ofNullable(
                        template.load(Key.builder().partitionValue(id).build(), Person.class))
                .orElseThrow(() -> new NotFoundException("Person not found with id: " + id));
        boolean hasAnimals = template
                .query(
                        QueryEnhancedRequest.builder()
                                .queryConditional(QueryConditional.keyEqualTo(
                                        Key.builder().partitionValue(id).build()))
                                .build(),
                        Animal.class)
                .stream()
                .flatMap(p -> p.items().stream())
                .findFirst()
                .isPresent();
        if (hasAnimals) {
            throw new PersonHasAssociatedAnimalsException(person.getId());
        }
        template.delete(person);
        snsService.publishPersonEvent(PersonEventType.ACCOUNT_DELETED, person);
    }
}
