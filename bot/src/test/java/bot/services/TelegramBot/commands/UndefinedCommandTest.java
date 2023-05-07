package bot.services.TelegramBot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UndefinedCommandTest {
    @Mock
    public Update update;

    @Spy
    public UndefinedCommand undefinedCommand;

    @BeforeEach
    public void setUp(){
        Long chatId = 119L;
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(chat.id()).thenReturn(chatId);
        when(message.chat()).thenReturn(chat);
        when(update.message()).thenReturn(message);
    }

    @Test
    void handle() {
        //given
        String expectedMessage = "Unknown command";

        //when
        SendMessage sendMessage = undefinedCommand.handle(update);

        //then
        assertEquals(expectedMessage, sendMessage.getParameters().get("text"));
    }
}