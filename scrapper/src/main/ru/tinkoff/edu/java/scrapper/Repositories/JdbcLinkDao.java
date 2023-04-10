package scrapper.Repositories;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import scrapper.domains.Link;

import java.util.List;

@Repository
public class JdbcLinkDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Link> getAll() {
        return jdbcTemplate.query("SELECT * FROM link_subscription", new DataClassRowMapper<>(Link.class));
    }

    public List<Link> getAllByTgUserId(Long chatId){
        return jdbcTemplate.query("SELECT * FROM link_subscription WHERE tguserid=?", new Object[]{chatId}, new DataClassRowMapper<>(Link.class));
    }


    public Link add(Link link) {
        return jdbcTemplate.queryForObject("INSERT INTO link_subscription VALUES (nextval('link_subscription_id_seq'), ?, ?, ?) returning *",
                    new Object[] {link.getTgUserId(), link.getLink(), link.getLastUpdate()}, new DataClassRowMapper<>(Link.class));
    }

    public Link removeByLinkAndTgUserId(String url, Long chatId){
        var queryResult = jdbcTemplate.query("DELETE FROM link_subscription WHERE link=? and tguserid=? returning *",
                new Object[]{url, chatId}, new DataClassRowMapper<>(Link.class));
        if (queryResult.isEmpty()) return null;
        else return queryResult.get(0);
    }
}
