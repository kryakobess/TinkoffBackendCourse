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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ListCommandTest {

    @Mock
    public Update update;

    @Autowired
    public ListCommand listCommand;

    @MockBean
    public ScrapperClient scrapperClient;

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

        //when
        SendMessage sendMessage = listCommand.handle(update);

        assertEquals("You do not have any links in subscription", sendMessage.getParameters().get("text"));
    }

    @Test
    void handle_withNotEmptyLinksList() {
        //given
        List<LinkScrapperResponse> links = List.of(
                        new LinkScrapperResponse(1, URI.create("https://github.com/kryakobess/TinkoffBackendCourse/pulls")),
                        new LinkScrapperResponse(2, URI.create("https://www.youtube.com/watch?v=SjDMW-6vh58"))
                );

        when(listCommand.scrapperClient.getLinks(anyLong())).thenReturn(new ListLinksScrapperResponse(links.size(), links));
        String expectedMessage = links.stream().map(lr -> lr.url().toString()).collect(Collectors.joining("\n"));

        //when
        SendMessage sendMessage = listCommand.handle(update);

        assertEquals(expectedMessage, sendMessage.getParameters().get("text"));
    }
}