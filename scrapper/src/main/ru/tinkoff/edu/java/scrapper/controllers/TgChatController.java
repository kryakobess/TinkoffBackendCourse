package scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scrapper.DTOs.responses.ApiErrorResponse;

@RestController
@RequestMapping("/tg-chat")
public class TgChatController {
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void registerChat(@PathVariable int id){

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteChat(@PathVariable int id){

    }
}
