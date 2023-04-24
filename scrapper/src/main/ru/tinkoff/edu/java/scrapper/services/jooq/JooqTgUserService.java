package scrapper.services.jooq;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.Repositories.jooq.JooqTelegramUserRepository;
import scrapper.domains.TelegramUser;
import scrapper.services.TgUserService;

public class JooqTgUserService implements TgUserService {

    final JooqTelegramUserRepository userRepository;

    public JooqTgUserService(JooqTelegramUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void register(Long chatId) {
        var res = userRepository.add(new TelegramUser(chatId));
        if (res == null) throw new ScrapperBadRequestException("User with this id already exists");
    }

    @Override
    @Transactional
    public void delete(Long chatId) {
        var res = userRepository.removeByChatId(chatId);
        if (res == null) throw new ScrapperNotFoundException("Id " + chatId + " is not registered");
    }

    @Override
    @Transactional(readOnly = true)
    public TelegramUser getUserById(Long id) {
        var res = userRepository.getById(id);
        if (res == null) throw new ScrapperNotFoundException("There is no user with id = " + id);
        return res;
    }
}
