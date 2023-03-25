package bot.services;

import bot.DTOs.responses.LinkResponse;
import bot.DTOs.responses.ListLinksResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperClient {
    @GetExchange("/links")
    ListLinksResponse getLinks(@RequestParam("Tg-Chat-Id") int tgChatId);

   

}
