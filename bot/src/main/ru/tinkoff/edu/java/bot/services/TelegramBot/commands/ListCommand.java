package bot.services.TelegramBot.commands;

import bot.DTOs.responses.LinkScrapperResponse;
import bot.services.ScrapperClient;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class ListCommand implements Command {

    final ScrapperClient scrapperClient;

    public ListCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
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
        return getMessageWithSubscribedLinks(chatId);
    }

    private String getMessageWithSubscribedLinks(Long chatId){
        log.info("Getting all subscribed links for " + chatId);
        var links = scrapperClient.getLinks(chatId).links();
        if (links.isEmpty()){
            return "You do not have any links in subscription";
        }
        return links.stream().map(l -> l.url().toString()).collect(Collectors.joining("\n"));
    }
}
