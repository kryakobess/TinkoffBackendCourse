package scrapper.DTOs.responses;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LinkResponse(
        int id,
        @NotNull
        String url
) {
}
