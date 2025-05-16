package dev.luanfernandes.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Chave de paginação para busca paginada")
public record PaginationKey(
        @Schema(
                        description = "ID usado como referência para a próxima página",
                        example = "123e4567-e89b-12d3-a456-426614174000")
                String id) {}
