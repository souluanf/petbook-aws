package dev.luanfernandes.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Requisição para atualização de um animal")
public record AnimalUpdateRequest(
        @Schema(description = "ID da pessoa dona do animal", example = "321e4567-e89b-12d3-a456-426614174111")
                String personId,
        @Schema(description = "Nome do animal", example = "Rex") String name,
        @Schema(description = "Espécie do animal", example = "Cachorro") String species) {}
