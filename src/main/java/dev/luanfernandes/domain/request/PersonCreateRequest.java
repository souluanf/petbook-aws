package dev.luanfernandes.domain.request;

import dev.luanfernandes.domain.validation.ValidBrazilianPhone;
import dev.luanfernandes.domain.validation.ValidBrazilianZipcode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
@Schema(description = "Requisição para criação de uma nova pessoa")
public record PersonCreateRequest(
        @NotBlank(message = "Name is required")
                @Schema(description = "Nome completo da pessoa", example = "João Silva", maxLength = 255)
                String name,
        @NotBlank(message = "Email is required")
                @Email(message = "Email is invalid")
                @Schema(description = "Endereço de e-mail da pessoa", example = "joao.silva@email.com", maxLength = 255)
                String email,
        @NotBlank(message = "Phone is required")
                @ValidBrazilianPhone
                @Schema(description = "Número de telefone brasileiro no formato válido", example = "(11) 91234-5678")
                String phone,
        @Schema(description = "Zipcode of the person", example = "08210010", maxLength = 8)
                @NotBlank(message = "Is required")
                @ValidBrazilianZipcode
                String zipCode,
        @Schema(description = "Complemento do endereço", example = "Apto 101", maxLength = 255)
                String addressComplement,
        @NotBlank(message = "addressBuildingNumber is required")
                @Schema(description = "Número do endereço", example = "123", maxLength = 10)
                String addressBuildingNumber) {}
