package scrapper.services.jpa;

import org.springframework.stereotype.Service;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.Repositories.jpa.JpaLinkRepository;
import scrapper.Repositories.jpa.JpaTelegramUserRepository;
import scrapper.domains.Link;
import scrapper.domains.jpa.LinkEntity;
import scrapper.domains.jpa.TelegramUserEntity;
import scrapper.services.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class JpaLinkService implements LinkService {

    final JpaTelegramUserRepository userRepository;

    final JpaLinkRepository linkRepository;

    public JpaLinkService(JpaTelegramUserRepository userRepository, JpaLinkRepository linkRepository) {
        this.userRepository = userRepository;
        this.linkRepository = linkRepository;
    }

    @Override
    public Link add(Long chatId, URI url) {
        var user = userRepository.getByChatId(chatId);
        if (user == null) throw new ScrapperNotFoundException("User with id = " + chatId + " does not registered");
        var linkEntity = buildLinkEntityWithUserEntityAndUrl(user, url);
        return convertLinkEntityToLink(linkRepository.save(linkEntity));
    }

    @Override
    public Link remove(Long chatId, URI url) {
        var user = userRepository.getByChatId(chatId);
        if (user == null) throw new ScrapperNotFoundException("User with id = " + chatId + " does not registered");
        var linkToDelete = linkRepository.getByTgUserIdAndLink(user, url.toString());
        if (linkToDelete == null) throw new ScrapperNotFoundException("Link with url = " + url + " does not subscribed");
        linkRepository.delete(linkToDelete);
        return convertLinkEntityToLink(linkToDelete);
    }

    @Override
    public List<Link> getAll(Long chatId) {
        var user = userRepository.getByChatId(chatId);
        if (user == null) throw new ScrapperNotFoundException("User with id = " + chatId + " does not registered");
        var links = linkRepository.getLinkEntitiesByTgUserId(user);
        return links.stream().map(this::convertLinkEntityToLink).collect(Collectors.toList());
    }

    @Override
    public Link getLatestUpdatedLink() {
        var links = linkRepository.getLinkEntitiesOrderByLastUpdate();
        if (links.isEmpty()) throw new ScrapperBadRequestException("There is no links in data base");
        else return convertLinkEntityToLink(links.get(0));
    }

    @Override
    public void updateLinkById(Link linkWithUpdates) {
        var linkEntityToUpdate = linkRepository.findById(linkWithUpdates.getId());
        linkEntityToUpdate.ifPresentOrElse(
                (l) -> {
                    l.setLastUpdate(linkWithUpdates.getLastUpdate());
                    l.setLink(linkWithUpdates.getLink());
                    var userFromUpdate = userRepository.findById(linkWithUpdates.getTgUserId());
                    if (userFromUpdate.isPresent()){
                        l.setTgUserId(userFromUpdate.get());
                    } else {
                        throw new ScrapperNotFoundException("User with id = " + linkWithUpdates.getTgUserId() + " does not registered");
                    }
                    linkRepository.save(l);
                },
                () -> {throw new ScrapperNotFoundException("Link with id = " + linkWithUpdates.getId() + " does not exist");}
        );
    }

    private LinkEntity buildLinkEntityWithUserEntityAndUrl(TelegramUserEntity user, URI url){
        return LinkEntity.builder()
                .link(url.toString())
                .tgUserId(user)
                .lastUpdate(Timestamp.from(Instant.now()))
                .build();
    }

    private Link convertLinkEntityToLink(LinkEntity linkEntity){
        return Link.builder()
                .link(linkEntity.getLink())
                .id(linkEntity.getId())
                .tgUserId(linkEntity.getTgUserId().getId())
                .lastUpdate(linkEntity.getLastUpdate())
                .build();
    }
}
