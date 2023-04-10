package scrapper.Repositories;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import scrapper.domains.TelegramUser;

import java.util.List;

@Repository
public class JdbcTelegramUserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTelegramUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<TelegramUser> getAll() {
        return jdbcTemplate.query("SELECT * FROM tg_user", new DataClassRowMapper<>(TelegramUser.class));
    }

    public TelegramUser getByChatId(Long chatId){
        var queryResult = jdbcTemplate.query("SELECT * FROM tg_user WHERE chat_id=?",
                new Object[]{chatId}, new DataClassRowMapper<>(TelegramUser.class));
        if (queryResult.isEmpty()) return null;
        else {
            return queryResult.get(0);
        }
    }

    public TelegramUser add(TelegramUser telegramUser) {
        return jdbcTemplate.queryForObject("INSERT INTO tg_user VALUES (nextval('tg_user_id_seq'), ?) returning *",
                new Object[]{telegramUser.getChatId()}, new DataClassRowMapper<>(TelegramUser.class));
    }

    public TelegramUser removeByChatId(Long chatId) {
        var queryResult = jdbcTemplate.query("DELETE FROM tg_user WHERE chat_id=? returning *", new Object[]{chatId},
                new DataClassRowMapper<>(TelegramUser.class));
        if (queryResult.isEmpty()) return null;
        else {
            return queryResult.get(0);
        }
    }
}
