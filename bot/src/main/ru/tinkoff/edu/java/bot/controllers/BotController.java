package bot.controllers;

import bot.DTOs.requests.LinkUpdateRequest;
import bot.services.UpdateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BotController {

    final UpdateHandler handler;

    @PostMapping("/updates")
    @ResponseStatus(HttpStatus.OK)
    public void sendUpdate(@RequestBody LinkUpdateRequest linkUpdate) {
        log.info("received new update on http");
        handler.handle(linkUpdate);
    }
}
