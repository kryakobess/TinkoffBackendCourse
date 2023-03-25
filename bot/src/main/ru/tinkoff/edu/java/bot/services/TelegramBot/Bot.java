package bot.services.TelegramBot;

import com.pengrad.telegrambot.model.Update;

public interface Bot {
    void handle(Update update);
    void sendMessage(Long chatId, String message);

}
