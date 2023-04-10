package scrapper.Repositories;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import scrapper.domains.Link;

import java.util.List;

@Repository
public class JdbcLinkDao implements Dao<Link> {

    private final JdbcTemplate jdbcTemplate;

    public JdbcLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Link> getAll() {
        return jdbcTemplate.query("SELECT * FROM link_subscription", new DataClassRowMapper<>(Link.class));
    }

    @Override
    public void add(Link link) {
        jdbcTemplate.update("INSERT INTO link_subscription VALUES (?, ?, ?, ?)",
                link.getId(), link.getTgUserId(), link.getLink(), link.getLastUpdate());
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM link_subscription WHERE id=?", id);
    }

    public void removeByLink(String url){
        jdbcTemplate.update("DELETE FROM link_subscription WHERE link=?", url);
    }
}
