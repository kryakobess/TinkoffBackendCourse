package bot.services.TelegramBot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String getCommand();
    String getDescription();
    SendMessage handle(Update update);

    default BotCommand getBotCommand(){
        return new BotCommand(getCommand(), getDescription());
    }
}
