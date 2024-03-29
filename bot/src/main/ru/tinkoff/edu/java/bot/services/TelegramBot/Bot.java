package bot.services.TelegramBot;

import bot.configuration.BotConfig;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public abstract class Bot {

    protected TelegramBot telegramBot;

    abstract void handle(Update update);

    Bot(BotConfig botConfig) {
        telegramBot = new TelegramBot(botConfig.getToken());
        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(this::handle);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public void sendMessage(Long chatId, String message) {
        telegramBot.execute(new SendMessage(chatId, message));
    }
}
