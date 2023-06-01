package scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import scrapper.services.TgUserService;

@RestController
@RequestMapping("/tg-chat")
public class TgChatController {

    final TgUserService tgUserService;

    public TgChatController(TgUserService tgUserService) {
        this.tgUserService = tgUserService;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void registerChat(@PathVariable long id) {
       tgUserService.register(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteChat(@PathVariable long id) {
        tgUserService.delete(id);
    }
}
