package dev.luanfernandes.service.impl;

import static dev.luanfernandes.domain.util.PhotoKeyGenerator.generate;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import dev.luanfernandes.domain.entity.Animal;
import dev.luanfernandes.domain.entity.Person;
import dev.luanfernandes.domain.exception.InvalidFileTypeException;
import dev.luanfernandes.domain.exception.NotFoundException;
import dev.luanfernandes.domain.mapper.AnimalMapper;
import dev.luanfernandes.domain.request.AnimalCreateRequest;
import dev.luanfernandes.domain.request.AnimalUpdateRequest;
import dev.luanfernandes.domain.response.AnimalResponse;
import dev.luanfernandes.domain.response.PageResponse;
import dev.luanfernandes.domain.response.PaginationCursor;
import dev.luanfernandes.domain.response.PhotoDownload;
import dev.luanfernandes.service.AnimalService;
import dev.luanfernandes.webclient.aws.AwsS3Client;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import java.time.Instant;
import java.util.List;
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
public class AnimalServiceImpl implements AnimalService {

    private final AwsS3Client awsS3Client;
    private final DynamoDbTemplate template;
    private final AnimalMapper mapper;

    @Override
    public AnimalResponse create(AnimalCreateRequest request) {
        boolean exists = template
                .query(
                        QueryEnhancedRequest.builder()
                                .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                                        .partitionValue(request.personId())
                                        .build()))
                                .build(),
                        Person.class)
                .stream()
                .flatMap(p -> p.items().stream())
                .findFirst()
                .isPresent();

        if (!exists) {
            throw new NotFoundException("Person not found with id: " + request.personId());
        }

        Animal entity = mapper.toEntity(request);
        template.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public void uploadPhoto(String id, MultipartFile file) {
        Animal animalInfo = findByAnimalId(id);
        String personId = animalInfo.getPersonId();

        String contentType = file.getContentType();
        if (!IMAGE_JPEG_VALUE.equals(contentType) && !IMAGE_PNG_VALUE.equals(contentType)) {
            throw new InvalidFileTypeException("Tipo de arquivo não suportado. Apenas JPEG e PNG são permitidos.");
        }

        Animal animal = Optional.ofNullable(template.load(
                        Key.builder().partitionValue(personId).sortValue(id).build(), Animal.class))
                .orElseThrow(() -> new NotFoundException("Animal not found with id: " + id));

        String extension = IMAGE_PNG_VALUE.equals(contentType) ? "png" : "jpg";
        String key = generate("animals", id, extension);

        awsS3Client.uploadFile(key, file);

        animal.setPhotoKey(key);
        animal.setUpdatedAt(Instant.now());
        template.update(animal);
    }

    @Override
    public PhotoDownload downloadPhoto(String id) {
        Animal animalInfo = findByAnimalId(id);
        String personId = animalInfo.getPersonId();

        Animal animal = Optional.ofNullable(template.load(
                        Key.builder().partitionValue(personId).sortValue(id).build(), Animal.class))
                .orElseThrow(() -> new NotFoundException("Animal not found with id: " + id));

        String key = animal.getPhotoKey();
        if (key == null || key.isEmpty()) {
            throw new NotFoundException("Photo not uploaded");
        }
        byte[] bytes = awsS3Client.downloadFile(key);

        MediaType mediaType = key.endsWith(".png") ? IMAGE_PNG : IMAGE_JPEG;
        return new PhotoDownload(bytes, mediaType);
    }

    @Override
    public PageResponse<AnimalResponse> findAll(int pageSize, String paginationKey) {
        log.info("Find all animals");
        Map<String, AttributeValue> exclusiveStartKey = Optional.ofNullable(paginationKey)
                .filter(key -> !key.isBlank())
                .map(key -> {
                    String[] keys = key.split(":");
                    if (keys.length == 2) {
                        return Map.of(
                                "personId", AttributeValue.builder().s(keys[0]).build(),
                                "id", AttributeValue.builder().s(keys[1]).build());
                    }
                    throw new IllegalArgumentException("Invalid pagination key format. Expected 'personId:id'");
                })
                .orElse(null);

        Page<Animal> page = template
                .scan(
                        ScanEnhancedRequest.builder()
                                .limit(pageSize)
                                .exclusiveStartKey(exclusiveStartKey)
                                .build(),
                        Animal.class)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nenhum resultado retornado do scan."));

        String lastEvaluatedPersonId = Optional.ofNullable(page.lastEvaluatedKey())
                .map(m -> m.get("personId"))
                .map(AttributeValue::s)
                .orElse(null);

        String lastEvaluatedId = Optional.ofNullable(page.lastEvaluatedKey())
                .map(m -> m.get("id"))
                .map(AttributeValue::s)
                .orElse(null);

        PaginationCursor lastEvaluatedKey = (lastEvaluatedPersonId == null && lastEvaluatedId == null)
                ? null
                : PaginationCursor.builder()
                        .id(lastEvaluatedPersonId + ":" + lastEvaluatedId)
                        .build();

        return PageResponse.<AnimalResponse>builder()
                .items(page.items().stream().map(mapper::toResponse).toList())
                .lastEvaluatedKey(lastEvaluatedKey)
                .build();
    }

    @Override
    public Animal findByAnimalId(String animalId) {
        return template
                .query(
                        QueryEnhancedRequest.builder()
                                .queryConditional(QueryConditional.keyEqualTo(
                                        Key.builder().partitionValue(animalId).build()))
                                .build(),
                        Animal.class,
                        "animalIdIndex")
                .stream()
                .flatMap(p -> p.items().stream())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Animal not found with id: " + animalId));
    }

    @Override
    public AnimalResponse findById(String animalId) {
        Animal animal = findByAnimalId(animalId);
        return mapper.toResponse(animal);
    }

    @Override
    public List<AnimalResponse> findByPessoaId(String pessoaId) {
        boolean tutorExists =
                template.load(Key.builder().partitionValue(pessoaId).build(), Person.class) != null;
        if (!tutorExists) {
            throw new NotFoundException("Person not found with id: " + pessoaId);
        }
        var result = template
                .query(
                        QueryEnhancedRequest.builder()
                                .queryConditional(QueryConditional.keyEqualTo(
                                        Key.builder().partitionValue(pessoaId).build()))
                                .build(),
                        Animal.class)
                .stream()
                .flatMap(p -> p.items().stream())
                .toList();

        if (result.isEmpty()) {
            throw new NotFoundException("No animals found for person: " + pessoaId);
        }
        return result.stream().map(mapper::toResponse).toList();
    }

    @Override
    public AnimalResponse update(String animalId, AnimalUpdateRequest request) {
        Animal existing = findByAnimalId(animalId);
        mapper.updateFromRequest(request, existing);
        template.update(existing);
        return mapper.toResponse(existing);
    }

    @Override
    public void delete(String animalId) {
        Animal existing = findByAnimalId(animalId);
        template.delete(existing);
    }
}
