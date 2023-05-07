package bot.services.TelegramBot.commands;

import bot.DTOs.responses.LinkScrapperResponse;
import bot.services.ScrapperClient;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Component
public class ListCommandMock {
    @SpyBean
    public ListCommand listCommand;

    @SpyBean
    ScrapperClient client;

    public ListCommand getListCommand() {
        return listCommand;
    }

    public void setLinksList(List<String> links){
        when(client.getLinks(any()).links())
                .thenReturn(links.stream()
                        .map(l -> new LinkScrapperResponse(1, URI.create(l)))
                        .collect(Collectors.toList()));
    }
}
