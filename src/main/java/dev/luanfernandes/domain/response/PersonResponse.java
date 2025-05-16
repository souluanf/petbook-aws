package dev.luanfernandes.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import lombok.Builder;

@Builder
@Schema(description = "Person response")
public record PersonResponse(
        @Schema(
                        description = "Identificador único da pessoa",
                        example = "123e4567-e89b-12d3-a456-426614174000",
                        maxLength = 36,
                        type = "string",
                        format = "uuid")
                String id,
        @Schema(
                        description = "Nome completo da pessoa",
                        example = "João da Silva",
                        maxLength = 250,
                        minLength = 1,
                        pattern = ".*",
                        type = "string",
                        format = "text")
                String name,
        @Schema(
                        description = "Endereço de e-mail da pessoa",
                        example = "joao.silva@email.com",
                        maxLength = 320,
                        type = "string",
                        format = "email")
                String email,
        @Schema(
                        description = "Número de telefone da pessoa",
                        example = "+55 11 99999-8888",
                        maxLength = 20,
                        type = "string",
                        format = "text")
                String phone,
        @Schema(
                        description = "Chave da foto do perfil armazenada no S3",
                        example = "profile/photo123.jpg",
                        type = "string",
                        format = "text")
                String photoKey,
        @Schema(
                        description = "Estado do endereço da pessoa",
                        example = "SP",
                        maxLength = 2,
                        type = "string",
                        format = "text")
                String addressState,
        @Schema(
                        description = "Complemento do endereço da pessoa",
                        example = "Apto 101",
                        maxLength = 100,
                        type = "string",
                        format = "text")
                String addressComplement,
        @Schema(
                        description = "Rua do endereço da pessoa",
                        example = "Rua das Flores",
                        maxLength = 250,
                        type = "string",
                        format = "text")
                String addressStreet,
        @Schema(
                        description = "Número do prédio ou casa do endereço da pessoa",
                        example = "123",
                        maxLength = 10,
                        type = "string",
                        format = "text")
                String addressBuildingNumber,
        @Schema(
                        description = "Bairro do endereço da pessoa",
                        example = "Centro",
                        maxLength = 100,
                        type = "string",
                        format = "text")
                String addressNeighborhood,
        @Schema(
                        description = "Cidade do endereço da pessoa",
                        example = "São Paulo",
                        maxLength = 100,
                        type = "string",
                        format = "text")
                String addressCity,
        @Schema(
                        description = "Código IBGE da cidade do endereço da pessoa",
                        example = "3550308",
                        maxLength = 7,
                        type = "string",
                        format = "text")
                String addressIbgeCode,
        @Schema(
                        description = "CEP do endereço da pessoa",
                        example = "01001-000",
                        maxLength = 9,
                        type = "string",
                        format = "text")
                String addressZipCode,
        @Schema(
                        description = "Data de criação do registro",
                        example = "2024-04-22T18:30:00Z",
                        type = "string",
                        format = "date-time")
                Instant createdAt,
        @Schema(
                        description = "Data da última atualização do registro",
                        example = "2024-04-22T19:45:00Z",
                        type = "string",
                        format = "date-time")
                Instant updatedAt) {}
