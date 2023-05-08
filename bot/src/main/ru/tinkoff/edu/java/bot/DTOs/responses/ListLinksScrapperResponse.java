package bot.DTOs.responses;

import lombok.Builder;
import java.util.List;

@Builder
public record ListLinksScrapperResponse(
        int size,
        List<LinkScrapperResponse> links
) {
}
