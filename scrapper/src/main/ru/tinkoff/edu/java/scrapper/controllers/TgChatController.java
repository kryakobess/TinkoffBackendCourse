package scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.Repositories.ChatLinkRepository;
import scrapper.services.LinkService;
import scrapper.services.TgUserService;
import scrapper.services.jdbc.JdbcLinkService;

import java.util.HashSet;

@RestController
@RequestMapping("/tg-chat")
public class TgChatController {

    final TgUserService tgUserService;

    public TgChatController(TgUserService tgUserService) {
        this.tgUserService = tgUserService;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void registerChat(@PathVariable long id){
       tgUserService.register(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteChat(@PathVariable long id){
        tgUserService.delete(id);
    }
}
