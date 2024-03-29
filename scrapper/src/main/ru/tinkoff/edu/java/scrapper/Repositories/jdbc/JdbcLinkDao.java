package scrapper.Repositories.jdbc;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import scrapper.Repositories.LinkRepository;
import scrapper.domains.Link;

import java.util.List;

@Repository
public class JdbcLinkDao implements LinkRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Link> getAll() {
        return jdbcTemplate.query("SELECT * FROM link_subscription", new DataClassRowMapper<>(Link.class));
    }

    public List<Link> getAllByTgUserId(Long userId) {
        return jdbcTemplate.query("SELECT * FROM link_subscription WHERE tguserid=?",
            new Object[]{userId}, new DataClassRowMapper<>(Link.class));
    }

    public Link getLinkById(Long id) {
        var res =  jdbcTemplate.query("SELECT * FROM link_subscription WHERE id=?",
                new Object[]{id}, new DataClassRowMapper<>(Link.class));
        if (res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }

    public Link add(Link link) {
        return jdbcTemplate.queryForObject(
            """
                INSERT INTO link_subscription
                VALUES (nextval('link_subscription_id_seq'), ?, ?, ?)
                returning *
                """,
            new Object[] {link.getTgUserId(), link.getLink(), link.getLastUpdate()},
            new DataClassRowMapper<>(Link.class));
    }

    public Link removeByLinkAndTgUserId(String url, Long userId) {
        var queryResult = jdbcTemplate.query(
            """
                DELETE FROM link_subscription
                WHERE link=? and tguserid=?
                returning *
                """,
                new Object[]{url, userId}, new DataClassRowMapper<>(Link.class));
        if (queryResult.isEmpty()) {
            return null;
        } else {
            return queryResult.get(0);
        }
    }

    public List<Link> removeAllWithTgUserId(Long userId) {
        var queryResult = jdbcTemplate.query("DELETE FROM link_subscription WHERE tguserid=? returning *",
                new Object[]{userId}, new DataClassRowMapper<>(Link.class));
        return queryResult;
    }

    public List<Link> getAllLinksOrderByLastUpdate() {
        return jdbcTemplate.query("SELECT * FROM link_subscription ORDER BY lastupdate",
                new DataClassRowMapper<>(Link.class));
    }

    public void updateLinkById(Link upd) {
        jdbcTemplate.update("UPDATE link_subscription SET link = ?, lastupdate = ?, tguserid=? WHERE id=?",
                upd.getLink(), upd.getLastUpdate(), upd.getTgUserId(), upd.getId());
    }
}
