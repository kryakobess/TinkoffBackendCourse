package scrapper.services.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.IntegrationEnvironment;
import scrapper.Repositories.jpa.JpaLinkRepository;
import scrapper.Repositories.jpa.JpaTelegramUserRepository;
import scrapper.services.LinkService;
import scrapper.services.TgUserService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaLinkServiceTest extends IntegrationEnvironment {

    @Autowired
    TgUserService userService;

    @Autowired
    LinkService linkService;

    @Autowired
    JpaTelegramUserRepository userRepository;

    @Autowired
    JpaLinkRepository linkRepository;

    final List<Long> userChatIds = List.of(888L, 111L, 22L);

    @BeforeEach
    @Transactional
    @Rollback
    void addUsers(){
        for (var chatId : userChatIds){
            userService.register(chatId);
        }
    }

    @Test
    @Transactional
    @Rollback
    void addAndGetAllForExistingUsers() {
        //when
        var link = linkService.add(userChatIds.get(0), URI.create("https://www.baeldung.com/spring-data-partial-update"));
        assertNotNull(linkService.add(userChatIds.get(0), URI.create("https://www.baeldung.com/jpa-sort")));
        assertNotNull(linkService.add(userChatIds.get(0), URI.create("https://ru.wikipedia.org/wiki/Nmap")));
        assertNotNull(linkService.add(userChatIds.get(1), URI.create("https://ru.wikipedia.org/wiki/Nmap")));

        //then
        assertAll(
                () -> assertEquals(3, linkService.getAll(userChatIds.get(0)).size()),
                () -> assertEquals(1, linkService.getAll(userChatIds.get(1)).size()),
                () -> assertTrue( linkService.getAll(userChatIds.get(2)).isEmpty()),
                () -> assertEquals("https://www.baeldung.com/spring-data-partial-update", link.getLink()),
                () -> assertEquals(userRepository.getByChatId(userChatIds.get(0)).getId(), link.getTgUserId())
        );
    }

    @Test
    @Transactional
    @Rollback
    void addForUnknownUser(){
        //when
        try {
            linkService.add(72L, URI.create("https://www.baeldung.com/jpa-join-column"));
            fail();
        } catch (ScrapperNotFoundException e){
            //then
            assertTrue(true);
        }
    }

    @Test
    @Transactional
    @Rollback
    void removeExistingLink() {
        //given
        linkService.add(userChatIds.get(0), URI.create("https://www.baeldung.com/spring-data-partial-update"));

        //when
        var removedLink = linkService.remove(userChatIds.get(0),URI.create("https://www.baeldung.com/spring-data-partial-update"));

        //then
        assertEquals(0, linkService.getAll(userChatIds.get(0)).size());
        assertEquals("https://www.baeldung.com/spring-data-partial-update", removedLink.getLink());
        assertEquals(userRepository.getByChatId(userChatIds.get(0)).getId(), removedLink.getTgUserId());
    }

    @Test
    @Transactional
    @Rollback
    void removeUnknownLink(){
        //when
        try {
            linkService.remove(123L, URI.create("https://www.baeldung.com/jpa-join-column"));
            fail();
        } catch (ScrapperNotFoundException ex){
            assertTrue(true);
        }

        try {
            linkService.remove(userChatIds.get(0), URI.create("https://www.baeldung.com/jpa-join-column"));
            fail();
        } catch (ScrapperNotFoundException ex){
            assertTrue(true);
        }
    }

    @Test
    @Transactional
    @Rollback
    void getLatestUpdatedLink() {
        //given
        var latestAddedLink = linkService.add(userChatIds.get(0), URI.create("https://www.baeldung.com/spring-data-partial-update"));
        linkService.add(userChatIds.get(0), URI.create("https://www.baeldung.com/jpa-sort"));
        linkService.add(userChatIds.get(0), URI.create("https://ru.wikipedia.org/wiki/Nmap"));
        linkService.add(userChatIds.get(1), URI.create("https://ru.wikipedia.org/wiki/Nmap"));

        //when
        var res = linkService.getLatestUpdatedLink();

        //then
        assertAll(
                () -> assertEquals(latestAddedLink.getTgUserId(), res.getTgUserId()),
                () -> assertEquals(latestAddedLink.getLink(), res.getLink()),
                () -> assertEquals(latestAddedLink.getId(), res.getId())
        );
    }

    @Test
    @Transactional
    @Rollback
    void updateLinkById() {
        //given
        var link = linkService.add(userChatIds.get(0), URI.create("https://www.baeldung.com/jpa-sort"));
        link.setLastUpdate(Timestamp.from(Instant.now()));
        link.setLink("https://www.baeldung.com/jpa-join-column");

        //when
        linkService.updateLinkById(link);

        //then
        var updatedLink = linkRepository.findById(link.getId()).get();
        assertAll(
                () -> assertEquals(link.getLink(), updatedLink.getLink()),
                () -> assertEquals(link.getTgUserId(), updatedLink.getTgUserId().getId()),
                () -> assertEquals(link.getLastUpdate().toInstant().getEpochSecond(),
                        updatedLink.getLastUpdate().toInstant().getEpochSecond())
        );
    }

    @Test
    @Transactional
    @Rollback
    void removeUserWithSubscribedLinks(){
        //given
        var chatId = userChatIds.get(0);
        linkService.add(chatId, URI.create("https://www.baeldung.com/spring-data-partial-update"));
        linkService.add(chatId, URI.create("https://www.baeldung.com/jpa-sort"));
        linkService.add(chatId, URI.create("https://ru.wikipedia.org/wiki/Nmap"));

        //when
        userService.delete(chatId);

        //then
        try {
            linkService.getAll(chatId);
            fail();
        } catch (ScrapperNotFoundException e){
            assertTrue(true);
        }

    }
}