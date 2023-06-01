package scrapper.services;

import lombok.RequiredArgsConstructor;
import scrapper.DTOs.requests.TgBotLinkUpdateRequest;

@RequiredArgsConstructor
public class BotClientUpdateSender implements UpdateSender {

    final TelegramBotClient botClient;

    @Override
    public void send(TgBotLinkUpdateRequest update) {
        botClient.sendUpdate(update);
    }
}
