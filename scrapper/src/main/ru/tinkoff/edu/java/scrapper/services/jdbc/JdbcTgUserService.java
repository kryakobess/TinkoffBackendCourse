package scrapper.services.jdbc;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.Repositories.JdbcLinkDao;
import scrapper.Repositories.JdbcTelegramUserDao;
import scrapper.domains.TelegramUser;
import scrapper.services.TgUserService;

@Service("JdbcTgUserService")
public class JdbcTgUserService implements TgUserService {
    final JdbcTelegramUserDao userDao;
    final JdbcLinkDao linkDao;

    public JdbcTgUserService(JdbcTelegramUserDao userDao, JdbcLinkDao linkDao) {
        this.userDao = userDao;
        this.linkDao = linkDao;
    }

    @Override
    @Transactional
    public void register(Long chatId) {
        try {
            userDao.add(new TelegramUser(chatId));
        }catch (DuplicateKeyException ex){
            throw new ScrapperBadRequestException("User with chatId = " + chatId + " already exists");
        }
    }

    @Override
    @Transactional
    public void delete(Long chatId) {
        var user = userDao.getByChatId(chatId);
        if (user != null) {
            linkDao.removeAllWithTgUserId(user.getId());
            userDao.removeByChatId(chatId);
        }
        else {
            throw new ScrapperNotFoundException("User with chatId = " + chatId + " is not registered");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TelegramUser getUserById(Long id) {
        var user = userDao.getById(id);
        if (user == null) throw new ScrapperNotFoundException("There is no user with id = " + id);
        return user;
    }
}
