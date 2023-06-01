package bot.services.TelegramBot.commands;

import bot.DTOs.requests.DeleteLinkScrapperRequest;
import bot.services.ScrapperClient;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;

@Slf4j
@Component
public class UntrackCommand extends CommandWithReply {

    final ScrapperClient scrapperClient;

    public UntrackCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String getMessageToReply() {
        return "Send me a link you want unsubscribe from in reply to this message";
    }

    @Override
    public String getCommand() {
        return "/untrack";
    }

    @Override
    public String getDescription() {
        return "unsubscribe from link";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        if (isOriginalCommand(update)) {
            return sendMessageWithForceReply(chatId);
        } else if (isReplyToMessage(update)) {
            String linkFromMessage = update.message().text();
            unsubscribeFromLink(linkFromMessage, chatId);
            String message = "You have been unsubscribed from " + linkFromMessage;
            return new SendMessage(chatId, message);
        }
        return null;
    }

    private void unsubscribeFromLink(String link, Long chatId) {
        log.info("unsubscribing from" + link);
        try {
            scrapperClient.deleteLink(chatId, new DeleteLinkScrapperRequest(URI.create(link)));
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

}
