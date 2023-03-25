package bot.controllers;

import bot.DTOs.requests.LinkUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BotController {
    private final Map<String, LinkUpdateRequest> linkUpdateMap= new HashMap();
    @PostMapping("/updates")
    @ResponseStatus(HttpStatus.OK)
    public void sendUpdate(@RequestBody LinkUpdateRequest linkUpdate) {
        linkUpdateMap.put(linkUpdate.url(), linkUpdate);
    }
}
