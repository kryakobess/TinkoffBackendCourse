package scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import scrapper.DTOs.requests.AddLinkRequest;
import scrapper.DTOs.requests.RemoveLinkRequest;
import scrapper.DTOs.responses.LinkResponse;
import scrapper.DTOs.responses.ListLinksResponse;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.services.LinkService;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/links")
public class LinksController {
    final LinkService linkService;

    public LinksController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListLinksResponse getAllLinks(@RequestHeader("Tg-Chat-Id") long tgChatId){
        var links = linkService.getAll(tgChatId);
        return ListLinksResponse.builder()
                .size(links.size())
                .links(links.stream()
                        .map((link -> new LinkResponse(Math.toIntExact(link.getId()), link.getLink())))
                        .collect(Collectors.toList()))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LinkResponse addNewLink(@RequestHeader("Tg-Chat-Id") long tgChatId, @RequestBody AddLinkRequest addLinkRequest){
        try {
            var newLink = linkService.add(tgChatId, new URL(addLinkRequest.link()).toURI());
            return LinkResponse.builder()
                    .id(Math.toIntExact(newLink.getId()))
                    .url(newLink.getLink())
                    .build();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new ScrapperBadRequestException(e.getMessage());
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public LinkResponse deleteLink(@RequestHeader("Tg-Chat-Id") long tgChatId,  @RequestBody RemoveLinkRequest removeLinkRequest){
        try {
            var link = linkService.remove(tgChatId, new URL(removeLinkRequest.link()).toURI());
            return LinkResponse.builder()
                    .id(Math.toIntExact(link.getId()))
                    .url(link.getLink())
                    .build();
        } catch (MalformedURLException | URISyntaxException ex){
            throw new ScrapperBadRequestException(ex.getMessage());
        }
    }
}
