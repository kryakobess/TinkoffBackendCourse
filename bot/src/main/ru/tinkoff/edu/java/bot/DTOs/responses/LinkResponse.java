package bot.DTOs.responses;

import lombok.Builder;

@Builder
public record LinkResponse(
        int id,
        String url
) {
}
