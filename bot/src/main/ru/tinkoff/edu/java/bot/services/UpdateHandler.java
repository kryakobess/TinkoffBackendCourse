package bot.services;

import bot.DTOs.requests.LinkUpdateRequest;
import bot.services.TelegramBot.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateHandler {
    final Bot bot;

    public void handle(LinkUpdateRequest linkUpdate) {
        var chatId = linkUpdate.tgChatIds().get(0);
        var message = String.format("New update for %s :\n%s",
                linkUpdate.url(), linkUpdate.description());
        bot.sendMessage(chatId, message);
    }
}
