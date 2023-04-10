package scrapper.services;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.Repositories.JdbcTelegramUserDao;
import scrapper.domains.TelegramUser;

@Service
public class JdbcTgUserService implements TgUserService{
    final JdbcTelegramUserDao userDao;

    public JdbcTgUserService(JdbcTelegramUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void register(Long chatId) {
        try {
            userDao.add(new TelegramUser(chatId));
        }catch (DuplicateKeyException ex){
            throw new ScrapperBadRequestException("User with chatId = " + chatId + " already exists");
        }
    }

    @Override
    public void delete(Long chatId) {
        if (userDao.removeByChatId(chatId) == null){
            throw new ScrapperNotFoundException("User with chatId = " + chatId + " is not registered");
        }
    }
}
