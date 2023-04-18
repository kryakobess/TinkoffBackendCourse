package scrapper.Repositories;

import org.springframework.jdbc.core.DataClassRowMapper;
import scrapper.domains.TelegramUser;

import java.util.List;

public interface TelegramUserRepository {
    List<TelegramUser> getAll();

    TelegramUser getByChatId(Long chatId);

    TelegramUser getById(Long id);

    TelegramUser add(TelegramUser telegramUser);

    TelegramUser removeByChatId(Long chatId);
}
