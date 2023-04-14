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

    public List<Link> getAllByTgUserId(Long userId){
        return jdbcTemplate.query("SELECT * FROM link_subscription WHERE tguserid=?", new Object[]{userId}, new DataClassRowMapper<>(Link.class));
    }


    public Link add(Link link) {
        return jdbcTemplate.queryForObject("INSERT INTO link_subscription VALUES (nextval('link_subscription_id_seq'), ?, ?, ?) returning *",
                    new Object[] {link.getTgUserId(), link.getLink(), link.getLastUpdate()}, new DataClassRowMapper<>(Link.class));
    }

    public Link removeByLinkAndTgUserId(String url, Long userId){
        var queryResult = jdbcTemplate.query("DELETE FROM link_subscription WHERE link=? and tguserid=? returning *",
                new Object[]{url, userId}, new DataClassRowMapper<>(Link.class));
        if (queryResult.isEmpty()) return null;
        else return queryResult.get(0);
    }

    public List<Link> removeAllWithTgUserId(Long userId){
        var queryResult = jdbcTemplate.query("DELETE FROM link_subscription WHERE tguserid=? returning *",
                new Object[]{userId}, new DataClassRowMapper<>(Link.class));
        return queryResult;
    }

    public Link getLatestUpdatedLink(){
        var queryRes =  jdbcTemplate.query("SELECT * FROM link_subscription ORDER BY lastupdate LIMIT 1",
                new DataClassRowMapper<>(Link.class));
        return queryRes.get(0);
    }

    public void updateLinkById(Link upd){
        jdbcTemplate.update("UPDATE link_subscription SET link = ?, lastupdate = ?, tguserid=? WHERE id=?",
                new Object[]{upd.getLink(), upd.getLastUpdate(), upd.getTgUserId(), upd.getId()}, new DataClassRowMapper<>(Link.class));
    }
}
