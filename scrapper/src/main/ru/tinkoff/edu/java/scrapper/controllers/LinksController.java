package scrapper.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import scrapper.DTOs.requests.AddLinkRequest;
import scrapper.DTOs.requests.RemoveLinkRequest;
import scrapper.DTOs.responses.LinkResponse;
import scrapper.DTOs.responses.ListLinksResponse;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.services.LinkService;
import scrapper.services.jdbc.JdbcLinkService;

import java.net.MalformedURLException;
import java.net.URI;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/links")
public class LinksController {
    final LinkService linkService;

    public LinksController(@Qualifier("JooqLinkService") LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListLinksResponse getAllLinks(@RequestParam("Tg-Chat-Id") long tgChatId){
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
    public LinkResponse addNewLink(@RequestParam("Tg-Chat-Id") long tgChatId, @RequestBody AddLinkRequest addLinkRequest){
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
    public LinkResponse deleteLink(@RequestParam("Tg-Chat-Id") long tgChatId,  @RequestBody RemoveLinkRequest removeLinkRequest){
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

    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
