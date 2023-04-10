package scrapper.services;

public interface TgUserService {
    void register(Long chatId);
    void delete(Long chatId);
}
