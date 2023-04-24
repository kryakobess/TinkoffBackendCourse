package scrapper.DTOs.requests;

import lombok.Builder;

import java.net.URI;
import java.util.List;

@Builder
public record TgBotLinkUpdateRequest(
        int id,
        URI url,
        String description,
        List<Integer> tgChatIds
) {
}
