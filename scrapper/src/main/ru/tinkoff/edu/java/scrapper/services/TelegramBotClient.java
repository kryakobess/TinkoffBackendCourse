package scrapper.services;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import scrapper.DTOs.requests.TgBotLinkUpdateRequest;

public interface TelegramBotClient {
    @PostExchange(url = "/updates")
    void sendUpdate(@RequestBody TgBotLinkUpdateRequest linkUpdate);
}
