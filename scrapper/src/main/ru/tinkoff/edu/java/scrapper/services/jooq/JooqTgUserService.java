package scrapper.services.jooq;

import org.springframework.stereotype.Service;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.Repositories.JooqTelegramUserRepository;
import scrapper.domains.TelegramUser;
import scrapper.services.TgUserService;

@Service("JooqTgUserService")
public class JooqTgUserService implements TgUserService {

    final JooqTelegramUserRepository userRepository;

    public JooqTgUserService(JooqTelegramUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(Long chatId) {
        var res = userRepository.add(new TelegramUser(chatId));
        if (res == null) throw new ScrapperBadRequestException("User with this id already exists");
    }

    @Override
    public void delete(Long chatId) {
        var res = userRepository.removeByChatId(chatId);
        if (res == null) throw new ScrapperNotFoundException("Id " + chatId + " is not registered");
    }

    @Override
    public TelegramUser getUserById(Long id) {
        var res = userRepository.getById(id);
        if (res == null) throw new ScrapperNotFoundException("There is no user with id = " + id);
        return res;
    }
}
