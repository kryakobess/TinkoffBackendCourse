package scrapper.Repositories;

import java.util.List;
import scrapper.domains.TelegramUser;

public interface TelegramUserRepository {
    List<TelegramUser> getAll();

    TelegramUser getByChatId(Long chatId);

    TelegramUser getById(Long id);

    TelegramUser add(TelegramUser telegramUser);

    TelegramUser removeByChatId(Long chatId);
}
