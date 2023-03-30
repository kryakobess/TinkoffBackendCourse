package bot.services.TelegramBot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandFactory {
    Command[] commands = {
            new StartCommand(),
            new HelpCommand(),
            new ListCommand(),
            new TrackCommand(),
            new UntrackCommand()
    };

    public BotCommand[] getAllBotCommands(){
        return Arrays.stream(commands).map(Command::getBotCommand).toArray(BotCommand[]::new);
    }

    public Command getAppropriateCommandForMessageText(Update update){
        String text = update.message().text();
        switch (text){
            case "/start" ->{return new StartCommand();}
            case "/help" -> {return new HelpCommand();}
            case "/track" -> {return new TrackCommand();}
            case "/untrack" -> {return new UntrackCommand();}
            case "/list" -> {return new ListCommand();}
            default -> {return checkUnknownMessageAndGetCommand(update);}
        }
    }

    private Command checkUnknownMessageAndGetCommand(Update update){
        if (isReplyForTrackCommand(update)){
            return new TrackCommand();
        }
        else if (isReplyForUntrackCommand(update)){
            return new UntrackCommand();
        }
        else {
            return new UndefinedCommand();
        }
    }

    private boolean isReplyForTrackCommand(Update update){
        return new TrackCommand().isReplyToMessage(update);
    }

    private boolean isReplyForUntrackCommand(Update update){
        return new UntrackCommand().isReplyToMessage(update);
    }
}
