package bot.DTOs.requests;

import java.net.URI;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

@Builder
public record DeleteLinkScrapperRequest(
        @URL
        URI link
){

}
