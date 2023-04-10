package scrapper.Repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.IntegrationEnvironment;
import scrapper.domains.Link;
import scrapper.domains.TelegramUser;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcLinkDaoTest extends IntegrationEnvironment {

    @Autowired
    public JdbcLinkDao jdbcLinkDao;

    @Autowired
    public JdbcTelegramUserDao jdbcTelegramUserDao;

    private static final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")).withNano(0));

    @BeforeEach
    @Transactional
    @Rollback
    public void addSomeUsersInUsersDb(){
        List<TelegramUser> users = List.of(
                new TelegramUser(1L, 999L, "u1"),
                new TelegramUser(2L, 899L, "u2"),
                new TelegramUser(3L, 1331L, "u3"));

        for (var u : users){
            jdbcTelegramUserDao.add(u);
        }
    }

    private static Stream<Link> getTestLinks(){
        return Stream.of(
                new Link(1L, 3L, "https://stackoverflow.com/questions/3323618/handling-mysql-datetimes-and-timestamps-in-java", timestamp),
                new Link(2L, 3L, "https://www.baeldung.com/spring-jdbc-jdbctemplate", timestamp),
                new Link(3L, 1L, "https://stackoverflow.com/questions/75510595/cannot-detect-liquibase-log-file-liquibase-failed-to-start-because-no-changelog", timestamp)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestLinks")
    @Transactional
    @Rollback
    void addLinksToExistingUsers(Link link) {
        //given
        jdbcLinkDao.add(link);

        //when
        List<Link> res = jdbcLinkDao.getAll();

        System.out.println(res.get(0));
        //then
        assertEquals(link, res.get(0));
    }

    @Test
    @Transactional
    @Rollback
    void addLinksToUnknownUser(){
        //given
        Link link = new Link(1L, 999L, "https://www.baeldung.com/liquibase-refactor-schema-of-java-app", timestamp);

        try {
            //when
            jdbcLinkDao.add(link);
        } catch (Exception e){
            //then
            assertTrue(true);
            return;
        }
        fail();
    }

    @ParameterizedTest
    @MethodSource("getTestLinks")
    @Transactional
    @Rollback
    void removeLinkByURL(Link link) {
        //given
        jdbcLinkDao.add(link);

        //when
        List<Link> res = jdbcLinkDao.getAll();
        assertFalse(res.isEmpty());
        jdbcLinkDao.removeByLink(link.getLink());
        res = jdbcLinkDao.getAll();

        //then
        assertTrue(res.isEmpty());
    }
}