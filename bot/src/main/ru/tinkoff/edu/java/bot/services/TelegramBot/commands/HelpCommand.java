package bot.services.TelegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelpCommand implements Command{

    private static String HELP_MESSAGE = """
                                           /start -- register yourself to get updates
                                           /help -- show all commands
                                           /track -- subscribe to new link
                                           /untrack -- unsubscribe from link
                                           /list -- show all subscribed links
                                            """;

    @Override
    public String getCommand() {
        return "/help";
    }

    @Override
    public String getDescription() {
        return "show all commands";
    }

    @Override
    public SendMessage handle(Update update) {
        log.info("/help command");
        Long chatId = update.message().chat().id();
        return new SendMessage(chatId, HELP_MESSAGE);
    }
}
