package bot.services.TelegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ListCommand implements Command {

    List<String> links = new ArrayList<>();

    public ListCommand(){

    }

    @Override
    public String getCommand() {
        return "/list";
    }

    @Override
    public String getDescription() {
        return "show all subscribed links";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        String message = getListMessage(chatId);
        return new SendMessage(chatId, message);
    }

    private String getListMessage(Long chatId){
        log.info("Getting all subscribed links for " + chatId);
        if (hasSubscriptions(chatId)){
            return getListOfSubscribedLinksForChatId(chatId);
        }
        else {
            return "You do not have any links in subscription";
        }
    }

    private boolean hasSubscriptions(Long chatId){
        return !links.isEmpty();
    }

    private String getListOfSubscribedLinksForChatId(Long chatId){
        return String.join("\n\n", links);
    }
}
