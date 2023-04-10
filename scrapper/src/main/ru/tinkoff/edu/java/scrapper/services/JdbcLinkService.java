package scrapper.services;

import org.springframework.stereotype.Service;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.Repositories.JdbcLinkDao;
import scrapper.Repositories.JdbcTelegramUserDao;
import scrapper.domains.Link;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JdbcLinkService implements LinkService{

    final JdbcLinkDao linkDao;
    final JdbcTelegramUserDao telegramUserDao;

    public JdbcLinkService(JdbcLinkDao linkDao, JdbcTelegramUserDao telegramUserDao) {
        this.linkDao = linkDao;
        this.telegramUserDao = telegramUserDao;
    }

    @Override
    public Link add(Long chatId, URI url) {
        if (telegramUserDao.getByChatId(chatId) != null){
            return linkDao.add(new Link(chatId, url.toString(), Timestamp.valueOf(LocalDateTime.now())));
        } else {
            throw new ScrapperNotFoundException("ChatId " + chatId + " is not registered");
        }
    }

    @Override
    public Link remove(Long chatId, URI url) {
        var tgUser = telegramUserDao.getByChatId(chatId);
        if (tgUser != null) {
            return linkDao.removeByLinkAndTgUserId(url.toString(), tgUser.getId());
        }
        else {
            throw new ScrapperNotFoundException("ChatId " + chatId + " is not registered");
        }
    }

    @Override
    public List<Link> getAll(Long chatId) {
        var userWithId= telegramUserDao.getByChatId(chatId);
        if (userWithId != null){
            return linkDao.getAllByTgUserId(userWithId.getId());
        }
        else throw new ScrapperNotFoundException("ChatId " + chatId + " is not registered");
    }
}
