package dev.luanfernandes.domain.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import dev.luanfernandes.domain.entity.Animal;
import dev.luanfernandes.domain.request.AnimalCreateRequest;
import dev.luanfernandes.domain.request.AnimalUpdateRequest;
import dev.luanfernandes.domain.response.AnimalResponse;
import java.time.Instant;
import java.util.UUID;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = SPRING)
public interface AnimalMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "photoKey", ignore = true)
    Animal toEntity(AnimalCreateRequest request);

    AnimalResponse toResponse(Animal animal);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "photoKey", ignore = true)
    void updateFromRequest(AnimalUpdateRequest request, @MappingTarget Animal animal);

    @AfterMapping
    default void setTimestamps(@MappingTarget Animal animal) {
        Instant now = Instant.now();
        if (animal.getId() == null) {
            animal.setId(UUID.randomUUID().toString());
        }
        if (animal.getCreatedAt() == null) {
            animal.setCreatedAt(now);
        }
        animal.setUpdatedAt(now);
    }
}
