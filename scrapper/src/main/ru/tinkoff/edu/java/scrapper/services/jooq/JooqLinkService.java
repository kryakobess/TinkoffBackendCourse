package scrapper.services.jooq;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.Repositories.jooq.JooqLinkRepository;
import scrapper.Repositories.jooq.JooqTelegramUserRepository;
import scrapper.domains.Link;
import scrapper.services.LinkService;


public class JooqLinkService implements LinkService {

    final JooqLinkRepository linkRepository;
    final JooqTelegramUserRepository userRepository;

    public JooqLinkService(JooqLinkRepository linkRepository, JooqTelegramUserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public Link add(Long chatId, URI url) {
        var user = userRepository.getByChatId(chatId);
        if (user == null) {
            throw new ScrapperNotFoundException("User with chatId =" + chatId + " does not exist");
        }
        return linkRepository.add(new Link(user.getId(), url.toString(), Timestamp.from(Instant.now())));
    }

    @Override
    @Transactional
    public Link remove(Long chatId, URI url) {
        var user = userRepository.getByChatId(chatId);
        if (user == null) {
            throw new ScrapperNotFoundException("User with chatId = \" + chatId + \" does not exist");
        }
        var deletedLink = linkRepository.removeByLinkAndTgUserId(url.toString(), user.getId());
        if (deletedLink == null) {
            throw new ScrapperNotFoundException(chatId + " does not subscribe to this link");
        }
        return deletedLink;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Link> getAll(Long chatId) {
        var user = userRepository.getByChatId(chatId);
        if (user == null) {
            throw new ScrapperNotFoundException("There is no user with chatId = " + chatId);
        }
        return linkRepository.getAllByTgUserId(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Link getLatestUpdatedLink() {
        return linkRepository.getAllLinksOrderByLastUpdate().get(0);
    }

    @Override
    @Transactional
    public void updateLinkById(Link linkWithUpdates) {
        try {
            linkRepository.updateLinkById(linkWithUpdates);
        } catch (Exception ex) {
            throw new ScrapperNotFoundException("There is no link with id = " + linkWithUpdates.getId());
        }
    }
}
