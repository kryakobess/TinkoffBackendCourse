package scrapper.services;

import scrapper.domains.TelegramUser;

public interface TgUserService {

    void register(Long chatId);

    void delete(Long chatId);

    TelegramUser getUserById(Long id);
}
