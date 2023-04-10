package scrapper.services;

import scrapper.domains.Link;

import java.net.URI;
import java.util.List;

public interface LinkService {
    Link add(Long chatId, URI url);
    Link remove(Long chatId, URI url);
    List<Link> getAll(Long chatId);
}
