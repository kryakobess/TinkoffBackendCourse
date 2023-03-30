package bot.services.TelegramBot;

import bot.configuration.BotConfig;
import bot.services.TelegramBot.commands.CommandFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LinksNotifierBot implements Bot {

    private final CommandFactory commandFactory;
    private final TelegramBot telegramBot;


    public LinksNotifierBot(BotConfig botConfig, CommandFactory commandFactory) {
        telegramBot = new TelegramBot(botConfig.getToken());
        this.commandFactory = commandFactory;
        telegramBot.setUpdatesListener(updates ->{
            updates.forEach(this::handle);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
        telegramBot.execute(new SetMyCommands(commandFactory.getAllBotCommands()));
    }

    @Override
    public void handle(Update update) {
        if (hasMessage(update)) {
            Long chatId = update.message().chat().id();
            log.info("Handling update from " + chatId);
            if (isTextMessage(update)){
                telegramBot.execute(commandFactory.getAppropriateCommandForMessageText(update).handle(update));
            }
        }
    }
    private boolean hasMessage(Update update){
        return update.message() != null;
    }
    private boolean isTextMessage(Update update){
        return hasMessage(update) && update.message().text() != null;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        log.info("Sending response to " + chatId);
        telegramBot.execute(new SendMessage(chatId, message));
    }
}
