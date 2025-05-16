package dev.luanfernandes.domain.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Paginated response")
public class PageResponse<T> {

    @ArraySchema(schema = @Schema(description = "List of items returned"), maxItems = 9999)
    private List<T> items;

    @Schema(description = "Last evaluated key")
    private PaginationCursor lastEvaluatedKey;
}
