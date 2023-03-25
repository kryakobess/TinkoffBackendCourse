package bot.DTOs.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

import java.net.URI;

@Builder
public record AddLinkScrapperRequest(
        @URL
        URI link
) {
}
