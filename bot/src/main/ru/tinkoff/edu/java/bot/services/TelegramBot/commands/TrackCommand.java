package bot.services.TelegramBot.commands;

import bot.DTOs.requests.AddLinkScrapperRequest;
import bot.services.ScrapperClient;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;

@Slf4j
@Component
public class TrackCommand extends CommandWithReply {

    final ScrapperClient scrapperClient;

    public TrackCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String getMessageToReply() {
        return "Send me a link you want to track in reply to this message";
    }

    @Override
    public String getCommand() {
        return "/track";
    }

    @Override
    public String getDescription() {
        return "subscribe to new link";
    }

    @Override
    public SendMessage handle(Update update) {
        log.info("/track command");
        Long chatId = update.message().chat().id();
        if (isOriginalCommand(update)) {
            return sendMessageWithForceReply(chatId);
        } else if (isReplyToMessage(update)) {
            String linkFromText = update.message().text();
            subscribeLink(linkFromText, chatId);
            String message = "Link has been subscribed";
            return new SendMessage(chatId, message);
        }
        return null;
    }


    private void subscribeLink(String link, Long chatId) {
        log.info("Subscribing to " + link);
        try {
            scrapperClient.addNewLink(chatId, new AddLinkScrapperRequest(URI.create(link)));
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }
}
