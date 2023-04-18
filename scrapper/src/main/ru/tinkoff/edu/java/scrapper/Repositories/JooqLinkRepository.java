package scrapper.Repositories;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import scrapper.domains.Link;
import scrapper.domains.jooq.tables.LinkSubscription;
import scrapper.domains.jooq.tables.TgUser;
import scrapper.domains.jooq.tables.records.LinkSubscriptionRecord;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JooqLinkRepository implements LinkRepository{

    final DSLContext dslContext;

    final LinkSubscription ls = LinkSubscription.LINK_SUBSCRIPTION;
    final TgUser tg = TgUser.TG_USER;

    public JooqLinkRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public List<Link> getAll() {
        var res = dslContext.select().from(ls).fetch().into(LinkSubscriptionRecord.class);
        return res.stream().map(this::mapLinkWithRecord).collect(Collectors.toList());
    }

    @Override
    public List<Link> getAllByTgUserId(Long userId) {
        var res = dslContext.select().from(ls).where(ls.TGUSERID.eq(userId)).fetch().into(LinkSubscriptionRecord.class);
        return res.stream().map(this::mapLinkWithRecord).collect(Collectors.toList());
    }

    @Override
    public Link getLinkById(Long id) {
        var res = dslContext.select()
                .from(ls)
                .where(ls.ID.eq(id))
                .fetchOptional();
        return res.map(r -> mapLinkWithRecord(r.into(LinkSubscriptionRecord.class))).orElse(null);
    }

    @Override
    public Link add(Link link) {
        var res =  dslContext.insertInto(ls)
                .set(ls.LINK, link.getLink())
                .set(ls.TGUSERID, link.getTgUserId())
                .set(ls.LASTUPDATE, link.getLastUpdate().toLocalDateTime())
                .returning()
                .fetchOptional();
        return res.map(this::mapLinkWithRecord).orElse(null);
    }

    @Override
    public Link removeByLinkAndTgUserId(String url, Long userId) {
        var res = dslContext.deleteFrom(ls)
                .where(ls.LINK.eq(url).and(ls.TGUSERID.eq(userId)))
                .returning()
                .fetchOptional();
        return res.map(this::mapLinkWithRecord).orElse(null);
    }

    @Override
    public List<Link> removeAllWithTgUserId(Long userId) {
        var res = dslContext.deleteFrom(ls)
                .where(ls.TGUSERID.eq(userId))
                .returning()
                .fetch();
        return res.stream().map(this::mapLinkWithRecord).collect(Collectors.toList());
    }

    @Override
    public List<Link> getAllLinksOrderByLastUpdate() {
        var res = dslContext.select().from(ls).orderBy(ls.LASTUPDATE).fetch().into(LinkSubscriptionRecord.class);
        return res.stream().map(this::mapLinkWithRecord).collect(Collectors.toList());
    }

    @Override
    public void updateLinkById(Link upd) {
        dslContext.update(ls)
                .set(ls.LINK, upd.getLink())
                .set(ls.TGUSERID, upd.getTgUserId())
                .set(ls.LASTUPDATE, upd.getLastUpdate().toLocalDateTime())
                .where(ls.ID.eq(upd.getId()))
                .execute();
    }

    private Link mapLinkWithRecord(LinkSubscriptionRecord record){
        return new Link(record.getId(), record.getTguserid(), record.getLink(), Timestamp.valueOf(record.getLastupdate()));
    }

}
