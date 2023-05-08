package scrapper.Repositories;

import java.util.List;
import scrapper.domains.Link;

public interface LinkRepository {

    List<Link> getAll();

    List<Link> getAllByTgUserId(Long userId);

    Link getLinkById(Long id);

    Link add(Link link);

    Link removeByLinkAndTgUserId(String url, Long userId);

    List<Link> removeAllWithTgUserId(Long userId);

    List<Link> getAllLinksOrderByLastUpdate();

    void updateLinkById(Link upd);
}
