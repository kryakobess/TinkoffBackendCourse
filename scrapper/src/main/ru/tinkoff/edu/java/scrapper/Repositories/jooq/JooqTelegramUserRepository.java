package scrapper.Repositories.jooq;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import scrapper.Repositories.TelegramUserRepository;
import scrapper.domains.TelegramUser;
import scrapper.domains.jooq.tables.LinkSubscription;
import scrapper.domains.jooq.tables.TgUser;
import scrapper.domains.jooq.tables.records.TgUserRecord;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JooqTelegramUserRepository implements TelegramUserRepository {
    final DSLContext dslContext;

    final TgUser tgUser = TgUser.TG_USER;

    final LinkSubscription linkSubscription = LinkSubscription.LINK_SUBSCRIPTION;


    public JooqTelegramUserRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }


    @Override
    public List<TelegramUser> getAll() {
        var res = dslContext.select()
                .from(tgUser)
                .fetch()
                .into(TgUserRecord.class);
        return res.stream().map(this::mapRecordToTelegramUser).collect(Collectors.toList());
    }

    @Override
    public TelegramUser getByChatId(Long chatId) {
        var res = dslContext.select()
                .from(tgUser)
                .where(tgUser.CHAT_ID.eq(chatId))
                .fetchOptional();
        return res.map(r -> mapRecordToTelegramUser(r.into(TgUserRecord.class))).orElse(null);
    }

    @Override
    public TelegramUser getById(Long id) {
        var res = dslContext.select()
                .from(tgUser)
                .where(tgUser.ID.eq(id))
                .fetchOptional();
        return res.map(r -> mapRecordToTelegramUser(r.into(TgUserRecord.class))).orElse(null);
    }

    @Override
    public TelegramUser add(TelegramUser telegramUser) {
        var res = dslContext.insertInto(tgUser, tgUser.CHAT_ID)
                .values(telegramUser.getChatId())
                .returning()
                .fetchOptional();
        return res.map(this::mapRecordToTelegramUser).orElse(null);
    }

    @Override
    public TelegramUser removeByChatId(Long chatId) {
        dslContext.deleteFrom(linkSubscription)
                .where(linkSubscription.TGUSERID.in(
                        dslContext.select(tgUser.ID)
                                .from(tgUser)
                                .join(linkSubscription)
                                .on(tgUser.ID.eq(linkSubscription.TGUSERID))
                                .where(tgUser.CHAT_ID.eq(chatId))
                                .fetch()
                ))
                .execute();
        var res = dslContext.deleteFrom(tgUser)
                .where(tgUser.CHAT_ID.eq(chatId))
                .returning()
                .fetchOptional();
        return res.map(this::mapRecordToTelegramUser).orElse(null);
    }

    private TelegramUser mapRecordToTelegramUser(TgUserRecord record){
        return new TelegramUser(record.getId(), record.getChatId());
    }
}
