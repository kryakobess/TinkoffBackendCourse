package bot.controllers;

import bot.DTOs.requests.LinkUpdateRequest;
import bot.services.TelegramBot.Bot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BotController {
    final Bot bot;

    public BotController(Bot bot) {
        this.bot = bot;
    }

    @PostMapping("/updates")
    @ResponseStatus(HttpStatus.OK)
    public void sendUpdate(@RequestBody LinkUpdateRequest linkUpdate) {
        log.info("received new update");
        var chatId = linkUpdate.tgChatIds().get(0);
        var message = String.format("New update for %s :\n%s",
                linkUpdate.url(), linkUpdate.description());
        bot.sendMessage(chatId, message);
    }
}
