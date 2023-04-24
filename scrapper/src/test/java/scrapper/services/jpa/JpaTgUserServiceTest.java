package scrapper.services.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.IntegrationEnvironment;
import scrapper.Repositories.jpa.JpaTelegramUserRepository;
import scrapper.services.TgUserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaTgUserServiceTest extends IntegrationEnvironment {

    @Autowired
    TgUserService userService;

    @Autowired
    JpaTelegramUserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    void registerUsers() {
        userService.register(888L);
        userService.register(999L);
        userService.register(111L);

        assertEquals(3, userRepository.findAll().size());
    }

    @Test
    @Transactional
    @Rollback
    void registerMultipleUsers() {
        userService.register(888L);

        try {
            userService.register(888L);
            fail();
        } catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    @Transactional
    @Rollback
    void delete() {
        //given
        userService.register(888L);

        //when
        userService.delete(888L);

        //then
        var users = userRepository.findAll();
        assertTrue(users.isEmpty());
        try {
            userService.delete(888L);
            fail();
        } catch (ScrapperNotFoundException e){
            assertTrue(true);
        }
    }

    @Test
    @Transactional
    @Rollback
    void getUnknownUser(){
        assertNull(userRepository.getByChatId(888L));
    }

    @Test
    @Transactional
    @Rollback
    void getUserById(){
        //given
        userService.register(888L);
        var u = userRepository.getByChatId(888L);

        //when
        var res = userService.getUserById(u.getId());

        //then
        assertEquals(888L, res.getChatId());
        userService.delete(888L);
        try {
            userService.getUserById(1L);
            fail();
        } catch (ScrapperNotFoundException e){
            assertTrue(true);
        }
    }
}