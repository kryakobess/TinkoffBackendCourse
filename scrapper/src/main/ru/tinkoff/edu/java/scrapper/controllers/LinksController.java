package scrapper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scrapper.DTOs.requests.AddLinkRequest;
import scrapper.DTOs.requests.RemoveLinkRequest;
import scrapper.DTOs.responses.LinkResponse;
import scrapper.DTOs.responses.ListLinksResponse;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.Repositories.ChatLinkRepository;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/links")
public class LinksController {
    private final ChatLinkRepository chatLinkRepository;

    public LinksController(ChatLinkRepository chatLinkRepository) {
        this.chatLinkRepository = chatLinkRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListLinksResponse getAllLinks(@RequestParam("Tg-Chat-Id") int tgChatId){
        if (!chatLinkRepository.linksMap.containsKey(tgChatId)){
            throw new ScrapperBadRequestException("ChatID " + tgChatId + " have not been registered");
        }
        return ListLinksResponse.builder()
                .links(chatLinkRepository.linksMap.get(tgChatId).stream()
                        .map((el) -> new LinkResponse(tgChatId, el)).collect(Collectors.toList()))
                .size(chatLinkRepository.linksMap.get(tgChatId).size())
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewLink(@RequestParam("Tg-Chat-Id") int tgChatId, @RequestBody AddLinkRequest addLinkRequest){
        if (!chatLinkRepository.linksMap.containsKey(tgChatId)) {
            throw new ScrapperBadRequestException("ChatID " + tgChatId + " have not been registered");
        }
        if (!chatLinkRepository.linksMap.get(tgChatId).contains(addLinkRequest.link())){
            chatLinkRepository.linksMap.get(tgChatId).add(addLinkRequest.link());
        }
        else throw new ScrapperBadRequestException("Link " + addLinkRequest.link() + "already exists");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteLink(@RequestParam("Tg-Chat-Id") int tgChatId, @RequestBody RemoveLinkRequest removeLinkRequest){
        if (!chatLinkRepository.linksMap.containsKey(tgChatId)) {
            throw new ScrapperBadRequestException("ChatID " + tgChatId + " have not been registered");
        }
        if (chatLinkRepository.linksMap.get(tgChatId).remove(removeLinkRequest.link())){
            return;
        }
        else throw new ScrapperNotFoundException("There is no " + removeLinkRequest.link() + " to delete");
    }
}
