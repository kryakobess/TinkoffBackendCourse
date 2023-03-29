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

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LinksNotifierBot implements Bot {

    private final TelegramBot telegramBot;

    private final LinkSubscriptionRepository linkSubscriptionRepository;

    private static final String UNKNOWN_COMMAND_MESSAGE = "Извините, я не знаю этой команды:(";
    private static final String TRACK_NEW_LINK_MESSAGE_TO_REPLY = "Send me a link you want to track in reply to this message";
    private static final String UNTRACK_LINK_MESSAGE_TO_REPLY = "Send me a link you want unsubscribe from in reply to this message";
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
        if (hasMessage(update)) {
            Long chatId = update.message().chat().id();
            log.info("Handling update from " + chatId);
            if (isTextMessage(update)){
                String text = update.message().text();
                switch (text) {
                    case "/start" -> {
                        initializeUser(chatId);
                        sendStartMessage(update);
                    }
                    case "/help" -> {
                        sendHelpMessage(update);
                    }
                    case "/track" -> {
                        if (isInitialized(chatId)) {
                            sendTrackAskForReplyMessage(update);
                        }
                    }
                    case "/untrack" -> {
                        if (isInitialized(chatId)) {
                            sendUntrackAskForReplyMessage(update);
                        }
                    }
                    case "/list" -> {
                        if (isInitialized(chatId)) {
                            sendListMessage(update);
                        }
                    }
                    default -> {
                        if (isReplyToMessageText(update, TRACK_NEW_LINK_MESSAGE_TO_REPLY) && isInitialized(chatId)) {
                            trackLinkFromReplyAndSendMessage(update);
                        } else if (isReplyToMessageText(update, UNTRACK_LINK_MESSAGE_TO_REPLY) && isInitialized(chatId)) {
                            untrackLinkFromReplyAndSendMessage(update);
                        }
                        else {
                            sendMessageForUnknownCommand(update);
                        }
                    }
                }
            }
        }
    }
    private boolean hasMessage(Update update){
        return update.message() != null;
    }
    private boolean isTextMessage(Update update){
        return hasMessage(update) && update.message().text() != null;
    }

    private void initializeUser(Long chatId){
        if (!linkSubscriptionRepository.existsUserWithId(chatId)){
            log.info("Initializing new user with id: " + chatId);
            linkSubscriptionRepository.createNewUserWithId(chatId);
        }
    }

    private boolean isInitialized(Long id){
        return linkSubscriptionRepository.existsUserWithId(id);
    }

    private void sendStartMessage(Update update){
        log.info("Sending start message");
        Long chatId = update.message().chat().id();
        String userName = update.message().from().firstName();
        String message = "Welcome, " + userName + "!\n" +
                "Now, you can track your links!";
        sendMessage(chatId, message);
    }

    private void sendHelpMessage(Update update){
        log.info("/help command");
        Long chatId = update.message().chat().id();
        String message = getHelpMessage();
        sendMessage(chatId, message);
    }

    private String getHelpMessage(){
        return "Available commands:\n" + Arrays.stream(botCommands).
                map(com -> com.command() + " -- " + com.description()).
                collect(Collectors.joining("\n"));
    }

    private void sendTrackAskForReplyMessage(Update update){
        log.info("/track command");
        Long chatId = update.message().chat().id();
        sendMessageWithForceReply(chatId, TRACK_NEW_LINK_MESSAGE_TO_REPLY);
    }

    private void sendUntrackAskForReplyMessage(Update update){
        log.info("/untrack command");
        Long chatId = update.message().chat().id();
        sendMessageWithForceReply(chatId, UNTRACK_LINK_MESSAGE_TO_REPLY);
    }


    private void sendListMessage(Update update){
        log.info("/list command");
        Long chatId = update.message().chat().id();
        String message = "";
        if (linkSubscriptionRepository.isEmptyForId(chatId)){
            message = "You do not have any links in subscription";
        }
        else {
            message = "Your links:\n" + getListOfSubscribedLinksForChatId(chatId);
        }
        sendMessage(chatId, message);
    }

    private String getListOfSubscribedLinksForChatId(Long chatId){
        return String.join("\n\n", linkSubscriptionRepository.getAllLinksForId(chatId));
    }

    private boolean isReplyToMessageText(Update update, String messageText){
        return update.message().replyToMessage() != null && update.message().replyToMessage().text().equals(messageText);
    }

    private void trackLinkFromReplyAndSendMessage(Update update){
        log.info("Getting link to track from reply");
        Long chatId = update.message().chat().id();
        String link = update.message().text();
        String messageToSend = "";
        if (!isValidURL(link)){
            messageToSend = "Unfortunately, this is not url format";
        }
        else if (linkSubscriptionRepository.existsUserWithIdAndSubscribedLink(chatId, link)) {
            messageToSend = "You have already subscribed to this link";
        } else {
            linkSubscriptionRepository.addNewLinkToId(chatId, link);
            messageToSend = "You have been subscribed to " + link;
        }
        sendMessage(chatId, messageToSend);
    }

    private void untrackLinkFromReplyAndSendMessage(Update update){
        log.info("Trying to untrack link");
        Long chatId = update.message().chat().id();
        String link = update.message().text();
        String messageToSend = "";
        if (linkSubscriptionRepository.existsUserWithIdAndSubscribedLink(chatId, link)) {
            linkSubscriptionRepository.removeLinkForId(chatId, link);
            messageToSend = "You have been unsubscribed from " + link;
        } else {
            messageToSend = "Link with this name has not been subscribed";
        }
        sendMessage(chatId, messageToSend);
    }

    private void sendMessageForUnknownCommand(Update update){
        log.info("unknown command");
        Long chatId = update.message().chat().id();
        sendMessage(chatId, UNKNOWN_COMMAND_MESSAGE);
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        log.info("Sending response to " + chatId);
        telegramBot.execute(new SendMessage(chatId, message));
    }

    private void sendMessageWithForceReply(Long chatId, String message){
        telegramBot.execute(new SendMessage(chatId, message).replyMarkup(new ForceReply()));
    }

    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
