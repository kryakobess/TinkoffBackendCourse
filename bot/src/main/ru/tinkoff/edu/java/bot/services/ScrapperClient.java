package bot.services;

import bot.DTOs.requests.AddLinkScrapperRequest;
import bot.DTOs.requests.DeleteLinkScrapperRequest;
import bot.DTOs.responses.LinkScrapperResponse;
import bot.DTOs.responses.ListLinksScrapperResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface ScrapperClient {
    @GetExchange("/links")
    ListLinksScrapperResponse getLinks(@RequestHeader("Tg-Chat-Id") int tgChatId);

    @PostExchange("/links")
    LinkScrapperResponse addNewLink(@RequestHeader("Tg-Chat-Id") int tgChatId, @RequestBody AddLinkScrapperRequest link);

    @DeleteExchange("/links")
    LinkScrapperResponse deleteLink(@RequestHeader("Tg-Chat-Id") int tgChatId, @RequestBody DeleteLinkScrapperRequest link);

    @PostExchange("/tg-chat/{id}")
    void registerChat(@PathVariable int id);

    @DeleteMapping("/tg-chat/{id}")
    void deleteChat(@PathVariable int id);
}