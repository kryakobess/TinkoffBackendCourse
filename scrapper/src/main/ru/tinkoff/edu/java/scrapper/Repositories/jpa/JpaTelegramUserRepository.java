package scrapper.Repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrapper.domains.jpa.TelegramUserEntity;

@Repository
public interface JpaTelegramUserRepository extends JpaRepository<TelegramUserEntity, Long> {
    Integer deleteByChatId(Long chatId);
    TelegramUserEntity getByChatId(Long chatId);
}
