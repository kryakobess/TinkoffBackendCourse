package scrapper.DTOs.requests;

import java.net.URI;
import java.util.List;

public record TgBotLinkUpdateRequest(
        int id,
        URI url,
        String description,
        List<Integer> tgChatIds
) {
}
