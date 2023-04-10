package scrapper.Repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.IntegrationEnvironment;
import scrapper.domains.TelegramUser;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcTelegramUserDaoTest extends IntegrationEnvironment {

    @Autowired
    JdbcTelegramUserDao telegramUserDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static Stream<TelegramUser> getUsersForTest(){
        return Stream.of(
                new TelegramUser(1L, 211L, "FirstDude"),
                new TelegramUser(2L, 222L, "SecondDude"),
                new TelegramUser(3L, 233L, "ThirdDude")
        );
    }

    @ParameterizedTest
    @MethodSource("getUsersForTest")
    @Transactional
    @Rollback
    void AddNewUserAndGetHimByGetAllCommand(TelegramUser user) {
        //given
        telegramUserDao.add(user);

        //when
        List<TelegramUser> res = telegramUserDao.getAll();

        //then
        assertEquals(res.get(0), user);
    }

    @ParameterizedTest
    @MethodSource("getUsersForTest")
    @Transactional
    @Rollback
    void addUserAndDeleteIt(TelegramUser user){
        //given
        telegramUserDao.add(user);
        List<TelegramUser> users = telegramUserDao.getAll();
        assertEquals(users.size(), 1);

        //when
        telegramUserDao.removeByChatId(user.getChatId());
        users = telegramUserDao.getAll();

        //then
        assertEquals(users.size(), 0);
    }
}