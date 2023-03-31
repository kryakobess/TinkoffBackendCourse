package bot.services.TelegramBot.commands;

import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ListCommandMock {
    @SpyBean
    public ListCommand listCommand;

    public ListCommand getListCommand() {
        return listCommand;
    }

    public void setLinksList(List<String> links){
        listCommand.links = links;
    }
}
