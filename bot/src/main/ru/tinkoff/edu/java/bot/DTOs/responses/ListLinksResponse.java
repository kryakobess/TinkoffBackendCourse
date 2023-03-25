package bot.DTOs.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record ListLinksResponse(
        int size,
        List<LinkResponse> links
) {
}