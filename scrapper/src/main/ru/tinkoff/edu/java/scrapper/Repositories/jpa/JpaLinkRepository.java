package scrapper.Repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import scrapper.domains.jpa.LinkEntity;
import scrapper.domains.jpa.TelegramUserEntity;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    LinkEntity getByTgUserIdAndLink(TelegramUserEntity tgUserId, String link);
    List<LinkEntity> getLinkEntitiesByTgUserId(TelegramUserEntity tgUserId);

    @Query("select l from LinkEntity as l order by l.lastUpdate")
    List<LinkEntity> getLinkEntitiesOrderByLastUpdate();
}
