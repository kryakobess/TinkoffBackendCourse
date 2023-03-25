package bot.services.TelegramBot;

import bot.configuration.BotConfig;
import bot.repositories.LinkSubscriptionRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LinksNotifierBot implements Bot {

    private final TelegramBot telegramBot;

    private final LinkSubscriptionRepository linkSubscriptionRepository;

    private static final String UNKNOWN_COMMAND_MESSAGE = "Извините, я не знаю этой команды:(";
    private static final String TRACK_NEW_LINK_MESSAGE = "Send me a link you want to track";
    private static final String UNTRACK_LINK_MESSAGE = "Send me a link you want unsubscribe from";
    BotCommand[] botCommands = {
            new BotCommand("/start", "register yourself to get updates"),
            new BotCommand("/help", "show all commands"),
            new BotCommand("/track", "subscribe to new link"),
            new BotCommand("/untrack", "unsubscribe from link"),
            new BotCommand("/list", "show all subscribed links")
    };

    public LinksNotifierBot(BotConfig botConfig, LinkSubscriptionRepository linkSubscriptionRepository) {
        telegramBot = new TelegramBot(botConfig.getToken());
        telegramBot.setUpdatesListener(updates ->{
            updates.forEach(this::handle);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
        telegramBot.execute(new SetMyCommands(botCommands));
        this.linkSubscriptionRepository = linkSubscriptionRepository;
    }

    @Override
    public void handle(Update update) {
        if (update.message() != null) {
            Long chatId = update.message().chat().id();
            log.info("Handling update from " + chatId);
            if (update.message().text() != null){
                String text = update.message().text();
                String message = "";
                switch (text){
                    case "/start": {
                        message = start(update);
                        break;
                    }
                    case "/help": {
                        message = help();
                        break;
                    }
                    case "/track":{
                        if (linkSubscriptionRepository.subscriptions.containsKey(chatId)) {
                            prepareTrack(update);
                        }
                        break;
                    }
                    case "/untrack":{
                        if (linkSubscriptionRepository.subscriptions.containsKey(chatId)) {
                            prepareUntrack(update);
                        }
                        break;
                    }
                    case "/list":{
                        if (linkSubscriptionRepository.subscriptions.containsKey(chatId)) {
                            message = list(chatId);
                        }
                        break;
                    }
                    default:{
                        if (update.message().replyToMessage() != null) {
                            String replyText = update.message().replyToMessage().text();
                            if (linkSubscriptionRepository.subscriptions.containsKey(chatId)) {
                                if (replyText.equals(TRACK_NEW_LINK_MESSAGE)) {
                                    message = trackLinkFromReply(chatId, text);
                                }
                                if (replyText.equals(UNTRACK_LINK_MESSAGE)) {
                                    message = untrackLinkFromReply(chatId, text);
                                }
                            }
                        } else {
                            log.info("unknown command");
                            message = UNKNOWN_COMMAND_MESSAGE;
                        }
                    }
                }
                if (!message.isBlank()){
                    sendMessage(chatId, message);
                    if (message.equals(UNKNOWN_COMMAND_MESSAGE)){
                        sendMessage(chatId, help());
                    }
                }
            }
        }
    }

    private String start(Update update){
        log.info("/start command");
        if (!linkSubscriptionRepository.subscriptions.containsKey(update.message().chat().id())) {
            linkSubscriptionRepository.subscriptions.put(update.message().chat().id(), new ArrayList<>());
            return "Welcome, " + update.message().from().firstName() + "!\n" +
                    "You have been registered, so let's track down your links!";
        }
        else return "You are registered already;)";

    }

    private String help(){
        log.info("/help command");
        return "Available commands:\n" + Arrays.stream(botCommands).
                map(com -> com.command() + " -- " + com.description()).
                collect(Collectors.joining("\n"));
    }

    private void prepareTrack(Update update){
        log.info("/track command");
        telegramBot.execute(new SendMessage(update.message().chat().id(), TRACK_NEW_LINK_MESSAGE).replyMarkup(new ForceReply()));
    }
    private String trackLinkFromReply(Long chatId, String text){
        log.info("Getting tracked link from reply");
        if (linkSubscriptionRepository.subscriptions.get(chatId).contains(text)) {
            return "You have already subscribed to this link";
        } else {
            linkSubscriptionRepository.subscriptions.get(chatId).add(text);
            return "You have been subscribed to link " + text;
        }
    }

    private void prepareUntrack(Update update){
        log.info("/untrack command");
        telegramBot.execute(new SendMessage(update.message().chat().id(), UNTRACK_LINK_MESSAGE).replyMarkup(new ForceReply()));
    }

    private String untrackLinkFromReply(Long chatId, String text){
        log.info("Trying to untrack link");
        if (linkSubscriptionRepository.subscriptions.get(chatId).contains(text)) {
            linkSubscriptionRepository.subscriptions.get(chatId).remove(text);
            return "You have been unsubscribed from " + text;
        } else {
            return "Link with this name have not been subscribed";
        }
    }

    private String list(Long chatId){
        log.info("/list command");
        if (linkSubscriptionRepository.subscriptions.get(chatId).isEmpty()){
            return "You do not have any links in subscription";
        }
        return "Your links:\n" + String.join("\n\n", linkSubscriptionRepository.subscriptions.get(chatId));
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        log.info("Sending response to " + chatId);
        telegramBot.execute(new SendMessage(chatId, message));
    }
}
