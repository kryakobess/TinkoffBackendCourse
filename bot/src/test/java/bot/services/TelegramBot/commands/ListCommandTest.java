package bot.services.TelegramBot.commands;

import bot.DTOs.responses.LinkScrapperResponse;
import bot.DTOs.responses.ListLinksScrapperResponse;
import bot.services.ScrapperClient;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ListCommandTest {

    @Mock
    Update update;

    @Autowired
    ListCommand listCommand;

    @MockBean
    ScrapperClient scrapperClient;

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
    void handle_withNoLinks() {
        //given
        when(scrapperClient.getLinks(anyLong())).thenReturn(new ListLinksScrapperResponse(0, new ArrayList<>()));

       // when(listCommand.scrapperClient).thenReturn(scrapperClient);

        //when
        SendMessage sendMessage = listCommand.handle(update);

        assertEquals("You do not have any links in subscription", sendMessage.getParameters().get("text"));
    }

    @Test
    void handle_withNotEmptyLinksList() {
        //given
        List<String> links = List.of(
                        "https://github.com/kryakobess/TinkoffBackendCourse/pulls",
                        "https://www.youtube.com/watch?v=SjDMW-6vh58"
                );
        when(listCommand.scrapperClient.getLinks(anyLong())).thenReturn(
                new ListLinksScrapperResponse(anyInt(), links.stream()
                .map(l -> new LinkScrapperResponse(1, URI.create(l)))
                .collect(Collectors.toList())));
        String expectedMessage = String.join("\n", links);

        //when
        SendMessage sendMessage = listCommand.handle(update);

        assertEquals(expectedMessage, sendMessage.getParameters().get("text"));
    }
}