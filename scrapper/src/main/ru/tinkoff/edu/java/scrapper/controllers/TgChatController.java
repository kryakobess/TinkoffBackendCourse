package scrapper.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scrapper.services.TgUserService;

@RestController
@RequestMapping("/tg-chat")
public class TgChatController {

    final TgUserService tgUserService;

    public TgChatController(@Qualifier("JooqTgUserService") TgUserService tgUserService) {
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
