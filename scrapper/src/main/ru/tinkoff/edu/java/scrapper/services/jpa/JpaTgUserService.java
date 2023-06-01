package scrapper.services.jpa;

import org.springframework.transaction.annotation.Transactional;
import scrapper.Exceptions.ScrapperBadRequestException;
import scrapper.Exceptions.ScrapperNotFoundException;
import scrapper.Repositories.jpa.JpaTelegramUserRepository;
import scrapper.domains.TelegramUser;
import scrapper.domains.jpa.TelegramUserEntity;
import scrapper.services.TgUserService;

public class JpaTgUserService implements TgUserService {

    final JpaTelegramUserRepository userRepository;

    public JpaTgUserService(JpaTelegramUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void register(Long chatId) {
        try {
            userRepository.save(new TelegramUserEntity(chatId));
        } catch (Exception e) {
            throw new ScrapperBadRequestException("User with id = " + chatId + " already exists");
        }
    }

    @Override
    @Transactional
    public void delete(Long chatId) {
        var res = userRepository.deleteByChatId(chatId);
        if (res.equals(0)) {
            throw new ScrapperNotFoundException("User with id = " + chatId + " do not registered");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TelegramUser getUserById(Long id) {
        var userFromDB = userRepository.findById(id);
        if (userFromDB.isPresent()) {
            var u = userFromDB.get();
            return TelegramUser.builder()
                    .id(u.getId())
                    .chatId(u.getChatId())
                    .build();
        } else {
            throw new ScrapperNotFoundException("User with id = " + id + " do not registered");
        }
    }
}
