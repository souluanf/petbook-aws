package dev.luanfernandes.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(description = "Representação de um animal")
public record AnimalResponse(
        @Schema(description = "Identificador do animal", example = "123e4567-e89b-12d3-a456-426614174000") String id,
        @Schema(description = "Nome do animal", example = "Rex") String name,
        @Schema(description = "Espécie do animal", example = "Cachorro") String species,
        @Schema(description = "Data de criação do registro", example = "2024-04-22T18:30:00Z") Instant createdAt,
        @Schema(description = "ID da pessoa dona do animal", example = "321e4567-e89b-12d3-a456-426614174111")
                String personId) {}
