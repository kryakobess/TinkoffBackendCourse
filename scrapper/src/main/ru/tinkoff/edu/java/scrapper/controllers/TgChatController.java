package scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.Repositories.ChatLinkRepository;

import java.util.HashSet;

@RestController
@RequestMapping("/tg-chat")
public class TgChatController {

    private final ChatLinkRepository chatLinkRepository;

    public TgChatController(ChatLinkRepository chatLinkRepository) {
        this.chatLinkRepository = chatLinkRepository;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void registerChat(@PathVariable int id){
        if (chatLinkRepository.linksMap.containsKey(id)){
            throw new ScrapperBadRequestException("Chat with id" + id + " already exists");
        }
        else {
            chatLinkRepository.linksMap.put(id, new HashSet<>());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteChat(@PathVariable int id){
        if (!chatLinkRepository.linksMap.containsKey(id)){
            throw new ScrapperBadRequestException("Chat with id" + id + " have not been registered yet");
        }
        else {
            chatLinkRepository.linksMap.remove(id);
        }
    }
}
