package bot.controllers;

import bot.DTOs.requests.LinkUpdate;
import bot.DTOs.responses.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {
    @PostMapping("/updates")
    @ResponseStatus(HttpStatus.OK)
    public void sendUpdate(@RequestBody LinkUpdate linkUpdate) {

    }
}