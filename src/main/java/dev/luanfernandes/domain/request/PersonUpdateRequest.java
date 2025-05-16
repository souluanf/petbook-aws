package dev.luanfernandes.domain.request;

import dev.luanfernandes.domain.validation.ValidBrazilianPhone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import org.springframework.validation.annotation.Validated;

@Validated
@Schema(description = "Requisição para atualização de dados de uma pessoa")
public record PersonUpdateRequest(
        @Email(message = "Email is invalid")
                @Schema(
                        description = "Novo endereço de e-mail da pessoa",
                        example = "maria.souza@email.com",
                        maxLength = 255)
                String email,
        @Schema(description = "Novo nome da pessoa", example = "Maria Souza", maxLength = 255) String name,
        @ValidBrazilianPhone @Schema(description = "Novo número de telefone brasileiro", example = "(21) 99876-5432")
                String phone) {}
