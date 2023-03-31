package bot.services.TelegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartCommand implements Command{
    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public String getDescription() {
        return "register yourself to get updates";
    }

    @Override
    public SendMessage handle(Update update) {
        log.info("Sending start message");
        Long chatId = update.message().chat().id();

        registerUserId(chatId);

        String userName = update.message().from().firstName();
        String message = "Welcome, " + userName + "!\n" +
                "Now, you can track your links!";
        return new SendMessage(chatId, message);
    }

    private void registerUserId(Long chatId){
        log.info("Registering new user with id " + chatId);
    }
}
