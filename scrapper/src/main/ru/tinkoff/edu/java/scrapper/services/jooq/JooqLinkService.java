package scrapper.services.jooq;

import org.springframework.stereotype.Service;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.Repositories.JooqLinkRepository;
import scrapper.Repositories.JooqTelegramUserRepository;
import scrapper.domains.Link;
import scrapper.services.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service("JooqLinkService")
public class JooqLinkService implements LinkService {

    final JooqLinkRepository linkRepository;
    final JooqTelegramUserRepository userRepository;

    public JooqLinkService(JooqLinkRepository linkRepository, JooqTelegramUserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Link add(Long chatId, URI url) {
        var user = userRepository.getByChatId(chatId);
        if (user == null) throw new ScrapperNotFoundException("User with chatId =" + chatId + " does not exist");
        return linkRepository.add(new Link(user.getId(), url.toString(), Timestamp.from(Instant.now())));
    }

    @Override
    public Link remove(Long chatId, URI url) {
        var user = userRepository.getByChatId(chatId);
        if (user == null) throw new ScrapperNotFoundException("User with chatId = \" + chatId + \" does not exist");
        var deletedLink = linkRepository.removeByLinkAndTgUserId(url.toString(), user.getId());
        if (deletedLink == null) throw new ScrapperNotFoundException(chatId + " does not subscribe to this link");
        return deletedLink;
    }

    @Override
    public List<Link> getAll(Long chatId) {
        var user = userRepository.getByChatId(chatId);
        if (user == null) throw new ScrapperNotFoundException("There is no user with chatId = " + chatId);
        return linkRepository.getAllByTgUserId(user.getId());
    }

    @Override
    public Link getLatestUpdatedLink() {
        return linkRepository.getAllLinksOrderByLastUpdate().get(0);
    }

    @Override
    public void updateLinkById(Link linkWithUpdates) {
        try {
            linkRepository.updateLinkById(linkWithUpdates);
        } catch (Exception ex){
            throw new ScrapperNotFoundException("There is no link with id = " + linkWithUpdates.getId());
        }
    }
}
