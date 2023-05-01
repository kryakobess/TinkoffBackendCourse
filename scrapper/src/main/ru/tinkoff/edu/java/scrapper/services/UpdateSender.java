package scrapper.services;

import scrapper.DTOs.requests.TgBotLinkUpdateRequest;

public interface UpdateSender {
    void send(TgBotLinkUpdateRequest update);
}
