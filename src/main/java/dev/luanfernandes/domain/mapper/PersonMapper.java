package dev.luanfernandes.domain.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import dev.luanfernandes.domain.entity.Person;
import dev.luanfernandes.domain.request.PersonCreateRequest;
import dev.luanfernandes.domain.request.PersonUpdateRequest;
import dev.luanfernandes.domain.response.PersonResponse;
import dev.luanfernandes.webclient.opencep.response.OpenCepResponse;
import java.time.Instant;
import java.util.UUID;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = SPRING)
public interface PersonMapper {
    @Mapping(target = "addressState", ignore = true)
    @Mapping(target = "addressStreet", ignore = true)
    @Mapping(target = "addressBuildingNumber", ignore = true)
    @Mapping(target = "addressNeighborhood", ignore = true)
    @Mapping(target = "addressCity", ignore = true)
    @Mapping(target = "addressIbgeCode", ignore = true)
    @Mapping(target = "addressZipCode", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "photoKey", ignore = true)
    Person toEntity(PersonCreateRequest request);

    PersonResponse toResponse(Person pessoa);

    @Mapping(target = "addressState", ignore = true)
    @Mapping(target = "addressComplement", ignore = true)
    @Mapping(target = "addressStreet", ignore = true)
    @Mapping(target = "addressBuildingNumber", ignore = true)
    @Mapping(target = "addressNeighborhood", ignore = true)
    @Mapping(target = "addressCity", ignore = true)
    @Mapping(target = "addressIbgeCode", ignore = true)
    @Mapping(target = "addressZipCode", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "photoKey", ignore = true)
    void updateFromRequest(PersonUpdateRequest request, @MappingTarget Person pessoa);

    @AfterMapping
    default void enrichMetadata(@MappingTarget Person pessoa) {
        Instant now = Instant.now();
        if (pessoa.getId() == null) {
            pessoa.setId(UUID.randomUUID().toString());
        }
        if (pessoa.getCreatedAt() == null) {
            pessoa.setCreatedAt(now);
        }
        pessoa.setUpdatedAt(now);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "photoKey", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "addressState", source = "uf")
    @Mapping(target = "addressStreet", source = "logradouro")
    @Mapping(target = "addressNeighborhood", source = "bairro")
    @Mapping(target = "addressCity", source = "localidade")
    @Mapping(target = "addressIbgeCode", source = "ibge")
    @Mapping(target = "addressZipCode", source = "cep")
    @Mapping(target = "addressBuildingNumber", ignore = true)
    @Mapping(target = "addressComplement", ignore = true)
    void updateWithAddress(OpenCepResponse openCepResponse, @MappingTarget Person person);
}
