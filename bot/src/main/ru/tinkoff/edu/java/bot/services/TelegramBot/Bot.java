package bot.services.TelegramBot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;

public interface Bot {

    void handle(Update update);
    void sendMessage(Long chatId, String message);

}
