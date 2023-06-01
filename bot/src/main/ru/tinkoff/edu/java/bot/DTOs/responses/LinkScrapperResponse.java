package bot.DTOs.responses;

import lombok.Builder;
import org.hibernate.validator.constraints.URL;
import java.net.URI;

@Builder
public record LinkScrapperResponse(
        int id,
        @URL
        URI url
) {
}
