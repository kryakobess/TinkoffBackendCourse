package bot.services.TelegramBot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandFactory {
    final StartCommand startCommand;
    final HelpCommand helpCommand;
    final ListCommand listCommand;
    final TrackCommand trackCommand;
    final UntrackCommand untrackCommand;
    final UndefinedCommand undefinedCommand;

    List<Command> commands;

    public CommandFactory(StartCommand startCommand, HelpCommand helpCommand, ListCommand listCommand, TrackCommand trackCommand, UntrackCommand untrackCommand, UndefinedCommand undefinedCommand) {
        this.startCommand = startCommand;
        this.helpCommand = helpCommand;
        this.listCommand = listCommand;
        this.trackCommand = trackCommand;
        this.untrackCommand = untrackCommand;
        this.undefinedCommand = undefinedCommand;
        this.commands = List.of(startCommand, helpCommand, listCommand, trackCommand, untrackCommand, undefinedCommand);
    }

    public BotCommand[] getAllBotCommands(){
        return commands.stream().map(Command::getBotCommand).toArray(BotCommand[]::new);
    }

    public SendMessage sendMessageForCommandFromUpdate(Update update){
        for (Command command : commands){
            if (command.isCalledInUpdate(update)){
                return command.handle(update);
            }
        }
        return undefinedCommand.handle(update);
    }
}
