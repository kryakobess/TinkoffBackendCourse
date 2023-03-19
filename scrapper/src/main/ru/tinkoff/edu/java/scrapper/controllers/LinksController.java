package scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scrapper.DTOs.requests.AddLinkRequest;
import scrapper.DTOs.requests.RemoveLinkRequest;
import scrapper.DTOs.responses.LinkResponse;
import scrapper.DTOs.responses.ListLinksResponse;

@RestController
@RequestMapping("/links")
public class LinksController {

    @GetMapping
    public ListLinksResponse getAllLinks(@RequestParam("Tg-Chat-Id") int tgChatId){
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public LinkResponse addNewLink(@RequestParam("Tg-Chat-Id") int tgChatId, @RequestBody AddLinkRequest addLinkRequest){
        return null;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public LinkResponse deleteLink(@RequestParam("Tg-Chat-Id") int tgChatId, RemoveLinkRequest removeLinkRequest){
        return null;
    }
}
