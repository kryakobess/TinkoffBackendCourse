package bot.services.TelegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UntrackCommand extends CommandWithReply{

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
        if (isOriginalCommand(update)){
            return sendMessageWithForceReply(chatId);
        }
        else if (isReplyToMessage(update)){
            String linkFromMessage = update.message().text();
            unsubscribeFromLink(linkFromMessage);
            String message = "You have been unsubscribed from " + linkFromMessage;
            return new SendMessage(chatId, message);
        }
        return null;
    }

    private void unsubscribeFromLink(String link){
        log.info("unsubscribing from" + link);
    }

}
