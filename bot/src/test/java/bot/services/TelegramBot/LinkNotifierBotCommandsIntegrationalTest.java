package bot.services.TelegramBot;

import bot.services.TelegramBot.commands.CommandFactory;
import bot.services.TelegramBot.commands.ListCommand;
import bot.services.TelegramBot.commands.ListCommandMock;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.util.Assert.isInstanceOf;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LinkNotifierBotCommandsIntegrationalTest {
    @SpyBean
    ListCommandMock listCommandMock;

    @SpyBean
    public CommandFactory commandFactory;

    @Autowired
    public LinksNotifierBot bot;

    @Mock
    public Update update;

    static class RequestMatcher implements ArgumentMatcher<BaseRequest<SendMessage, SendResponse>>{
        String messageTextToCheck;

        RequestMatcher(String messageTextToCheck){
            this.messageTextToCheck = messageTextToCheck;
        }

        @Override
        public boolean matches(BaseRequest<SendMessage, SendResponse> request) {
            return request.getParameters() != null &&
                    request.getParameters().containsKey("text") &&
                    request.getParameters().get("text").equals(messageTextToCheck);
        }
    }

    @BeforeEach
    void setUp(){
        bot = spy(bot);
        bot.telegramBot = spy(bot.telegramBot);

        Long chatId = 119L;
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(chat.id()).thenReturn(chatId);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);
    }

    @Test
    public void listCommand_EmptyLinksResponseTest(){
        //given
        String emptyListMessage = "You do not have any links in subscription";
        ListCommand listCommand = listCommandMock.getListCommand();

        //when
        when(update.message().text()).thenReturn("/list");
        bot.handle(update);

        //then
        assertAll(
                () -> verify(bot).handle(update),
                () -> verify(bot.commandFactory).sendMessageForCommandFromUpdate(update),
                () -> verify(listCommand).handle(update),
                () -> verify(bot.telegramBot).execute(argThat(new RequestMatcher(emptyListMessage)))
        );
    }

    @Test
    public void listCommand_ListOfLinksIsReturned(){
        //given
        List<String> links = List.of("https://www.baeldung.com/mockito-void-methods");
        listCommandMock.setLinksList(links);
        ListCommand listCommand = listCommandMock.listCommand;
        String expectedMessage = String.join("\n\n", links);

        //when
        when(update.message().text()).thenReturn("/list");
        bot.handle(update);

        //then
        assertAll(
                () -> verify(bot).handle(update),
                () -> verify(bot.commandFactory).sendMessageForCommandFromUpdate(update),
                () -> verify(listCommand).handle(update),
                () -> verify(bot.telegramBot).execute(argThat(new RequestMatcher(expectedMessage)))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"unknown", "command", "gets", "unknown mesage"})
    public void undefinedCommand_CheckUnknownCommandMessage(String commandText){
        //given
        String unknownCommandMessage = "Unknown command";

        //when
        when(update.message().text()).thenReturn(commandText);

        //then
        bot.handle(update);

        assertAll(
                () -> verify(bot).handle(update),
                () -> verify(bot.commandFactory).sendMessageForCommandFromUpdate(update),
                () -> verify(bot.telegramBot).execute(argThat(new RequestMatcher(unknownCommandMessage)))
        );
    }

}
