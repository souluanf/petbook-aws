package dev.luanfernandes.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
@Schema(description = "Requisição para criação de um animal")
public record AnimalCreateRequest(
        @Schema(description = "ID da pessoa dona do animal", example = "321e4567-e89b-12d3-a456-426614174111")
                @NotBlank(message = "Person ID is required")
                String personId,
        @Schema(description = "Nome do animal", example = "Rex") @NotBlank(message = "Name is required") String name,
        @Schema(description = "Espécie do animal", example = "Cachorro") @NotBlank(message = "Species is required")
                String species) {}
