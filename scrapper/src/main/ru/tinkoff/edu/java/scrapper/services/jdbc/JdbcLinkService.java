package scrapper.services.jdbc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.Repositories.JdbcLinkDao;
import scrapper.Repositories.JdbcTelegramUserDao;
import scrapper.domains.Link;
import scrapper.services.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JdbcLinkService implements LinkService {

    final JdbcLinkDao linkDao;
    final JdbcTelegramUserDao telegramUserDao;

    public JdbcLinkService(JdbcLinkDao linkDao, JdbcTelegramUserDao telegramUserDao) {
        this.linkDao = linkDao;
        this.telegramUserDao = telegramUserDao;
    }

    @Override
    @Transactional
    public Link add(Long chatId, URI url) {
        var tgUser = telegramUserDao.getByChatId(chatId);
        if (tgUser != null){
            return linkDao.add(new Link(tgUser.getId(), url.toString(), Timestamp.valueOf(LocalDateTime.now())));
        } else {
            throw new ScrapperNotFoundException("ChatId " + chatId + " is not registered");
        }
    }

    @Override
    @Transactional
    public Link remove(Long chatId, URI url) {
        var tgUser = telegramUserDao.getByChatId(chatId);
        if (tgUser != null) {
            var res = linkDao.removeByLinkAndTgUserId(url.toString(), tgUser.getId());
            if (res == null) throw new ScrapperNotFoundException(url.toString() + " does not exist");
            return res;
        }
        else {
            throw new ScrapperNotFoundException("ChatId " + chatId + " is not registered");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Link> getAll(Long chatId) {
        var tgUser = telegramUserDao.getByChatId(chatId);
        if (tgUser != null){
            return linkDao.getAllByTgUserId(tgUser.getId());
        }
        else throw new ScrapperNotFoundException("ChatId " + chatId + " is not registered");
    }

    @Override
    @Transactional
    public Link getLatestUpdatedLink() {
        var links = linkDao.getAllLinksOrderByLastUpdate();
        if (links.isEmpty()) throw new ScrapperBadRequestException("There is no links in data base");
        else return links.get(0);
    }

    @Override
    @Transactional
    public void updateLinkById(Link linkWithUpdates) {
        if (linkDao.getLinkById(linkWithUpdates.getId()) != null){
            linkDao.updateLinkById(linkWithUpdates);
        } else {
            throw new ScrapperNotFoundException("There is no link with id = " + linkWithUpdates.getId());
        }

    }
}