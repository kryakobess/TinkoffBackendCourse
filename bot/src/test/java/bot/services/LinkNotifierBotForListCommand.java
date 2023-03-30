package bot.services;

import bot.repositories.LinkSubscriptionRepository;
import bot.services.TelegramBot.LinksNotifierBot;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LinkNotifierBotForListCommand {

    @MockBean
    private LinkSubscriptionRepository linkSubscriptionRepository;

    @Mock
    private LinksNotifierBot bot;
    @Mock
    private Update update;


    @Test
    public void linksCommand_EmptyLinksResponseTest(){
        //given
        Long chatId = 119L;

        //when
        when(update.message().chat().id()).thenReturn(chatId);
        when(update.message().text()).thenReturn("/list");
        

        //then
        bot.handle(update);
    }

}
