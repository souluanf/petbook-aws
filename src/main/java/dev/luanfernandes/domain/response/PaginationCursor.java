package dev.luanfernandes.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Last key for paginated requests")
public record PaginationCursor(@Schema(description = "Last evaluated ID") String id) {}
