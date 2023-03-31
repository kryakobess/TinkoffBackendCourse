package bot.services.TelegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;

public abstract class CommandWithReply implements Command{
    public abstract String getMessageToReply();
    protected boolean isOriginalCommand(Update update){
        return update.message().text().equals(getCommand());
    }

    protected SendMessage sendMessageWithForceReply(Long chatId){
        return new SendMessage(chatId, getMessageToReply()).replyMarkup(new ForceReply());
    }

    public boolean isReplyToMessage(Update update){
        return update.message().replyToMessage() != null && update.message().replyToMessage().text().equals(getMessageToReply());
    }

    @Override
    public boolean isCalledInUpdate(Update update) {
        return isReplyToMessage(update) || isOriginalCommand(update);
    }
}
