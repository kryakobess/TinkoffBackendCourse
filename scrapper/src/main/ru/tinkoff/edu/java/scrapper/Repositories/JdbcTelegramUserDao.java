package scrapper.Repositories;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import scrapper.domains.TelegramUser;

import java.util.List;

@Repository
public class JdbcTelegramUserDao implements Dao<TelegramUser> {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTelegramUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TelegramUser> getAll() {
        return jdbcTemplate.query("SELECT * FROM tg_user", new DataClassRowMapper<>(TelegramUser.class));
    }

    @Override
    public void add(TelegramUser telegramUser) {
        jdbcTemplate.update("INSERT INTO tg_user VALUES (?, ?, ?)", telegramUser.getId(), telegramUser.getChatId(), telegramUser.getUserName());
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM tg_user WHERE id=?", id);
    }

    public void removeByChatId(Long chatId) {
        jdbcTemplate.update("DELETE FROM tg_user WHERE chat_id=?", chatId);
    }
}
