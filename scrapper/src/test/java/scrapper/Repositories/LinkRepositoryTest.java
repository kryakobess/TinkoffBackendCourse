package scrapper.Repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.IntegrationEnvironment;
import scrapper.domains.Link;
import scrapper.domains.TelegramUser;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LinkRepositoryTest extends IntegrationEnvironment {

    @Qualifier("jooqLinkRepository")
    @Autowired
    public LinkRepository linkRepository;

    @Qualifier("jooqTelegramUserRepository")
    @Autowired
    public TelegramUserRepository userRepository;

    private final List<TelegramUser> users = new ArrayList<>();

    private static final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")).withNano(0));

    @BeforeEach
    @Transactional
    @Rollback
    public void addSomeUsersInUsersDb(){
        users.add(new TelegramUser(1L, 999L));
        users.add(new TelegramUser(2L, 899L));
        users.add( new TelegramUser(3L, 1331L));

        for (int i = 0; i < users.size(); ++i){
            users.set(i, userRepository.add(users.get(i)));
        }
    }

    @Test
    @Transactional
    @Rollback
    void getEmptyLinkList(){
        assertTrue(linkRepository.getAll().isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void addLinksToExistingUsers() {
        //given
        var l1 = new Link(1L, users.get(0).getId(), "https://stackoverflow.com/questions/3323618/handling-mysql-datetimes-and-timestamps-in-java", timestamp);
        var l2 = new Link(2L, users.get(2).getId(), "https://www.baeldung.com/spring-jdbc-jdbctemplate", timestamp);
        var l3 = new Link(3L, users.get(2).getId(), "https://stackoverflow.com/questions/75510595/cannot-detect-liquibase-log-file-liquibase-failed-to-start-because-no-changelog", timestamp);
        var r1 = linkRepository.add(l1);
        linkRepository.add(l2);
        linkRepository.add(l3);
        System.out.println(r1);

        //when
        List<Link> res = linkRepository.getAll();

        //then
        assertEquals(l1, res.get(0));
        assertEquals(l2, res.get(1));
        assertEquals(l3, res.get(2));
    }

    @Test
    @Transactional
    @Rollback
    void addLinksToUnknownUser(){
        //given
        Link link = new Link(1L, 999L, "https://www.baeldung.com/liquibase-refactor-schema-of-java-app", timestamp);

        try {
            //when
            linkRepository.add(link);
        } catch (Exception e){
            //then
            assertTrue(true);
            return;
        }
        fail();
    }

    @Test
    @Transactional
    @Rollback
    void removeLinkByURLOrId() {
        //given
        var l1 = new Link(1L, users.get(0).getId(), "https://www.baeldung.com/spring-jdbc-jdbctemplate", timestamp);
        var l2 = new Link(2L, users.get(2).getId(), "https://www.baeldung.com/spring-jdbc-jdbctemplate", timestamp);
        var l3 = new Link(3L, users.get(2).getId(), "https://stackoverflow.com/questions/75510595/cannot-detect-liquibase-log-file-liquibase-failed-to-start-because-no-changelog", timestamp);
        linkRepository.add(l1);
        linkRepository.add(l2);
        linkRepository.add(l3);

        //when
        List<Link> res = linkRepository.getAll();
        assertFalse(res.isEmpty());
        linkRepository.removeByLinkAndTgUserId(l1.getLink(), users.get(0).getId());
        linkRepository.removeByLinkAndTgUserId(l2.getLink(), users.get(2).getId());
        linkRepository.removeByLinkAndTgUserId(l3.getLink(), users.get(2).getId());
        res = linkRepository.getAll();

        //then
        assertTrue(res.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void getAllByChatId(){
        //given
        var l1 = new Link(1L, users.get(0).getId(), "https://stackoverflow.com/questions/3323618/handling-mysql-datetimes-and-timestamps-in-java", timestamp);
        var l2 = new Link(2L, users.get(2).getId(), "https://www.baeldung.com/spring-jdbc-jdbctemplate", timestamp);
        var l3 = new Link(3L, users.get(2).getId(), "https://stackoverflow.com/questions/75510595/cannot-detect-liquibase-log-file-liquibase-failed-to-start-because-no-changelog", timestamp);
        linkRepository.add(l1);
        linkRepository.add(l2);
        linkRepository.add(l3);

        //when
        List<Link> res = linkRepository.getAllByTgUserId(users.get(2).getId());

        //then
        assertEquals(res.size(),2);
        assertEquals(res.get(0).getLink(), l2.getLink());
        assertEquals(res.get(0).getTgUserId(), l2.getTgUserId());
        assertEquals(res.get(1).getLink(), l3.getLink());
        assertEquals(res.get(1).getTgUserId(), l3.getTgUserId());
    }

    @Test
    @Transactional
    @Rollback
    public void removeUnknownLink(){
        //given
        assertNull(linkRepository.removeByLinkAndTgUserId("https://www.baeldung.com/java-optional", 123L));
    }
}