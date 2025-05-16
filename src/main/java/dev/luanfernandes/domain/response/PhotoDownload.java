package dev.luanfernandes.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;

@Schema(description = "Foto baixada com conteúdo e tipo de mídia")
public record PhotoDownload(
        @Schema(description = "Conteúdo binário da foto", type = "string", format = "binary") byte[] content,
        @Schema(description = "Tipo de mídia da foto (image/jpeg, image/png)", example = "image/jpeg", type = "string")
                MediaType contentType) {}
