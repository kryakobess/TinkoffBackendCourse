package scrapper.domains;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Link {
    Long id;
    Long tgUserId;
    String link;
    Timestamp lastUpdate;

    public Link(Long tgUserId, String link, Timestamp lastUpdate) {
        this.tgUserId = tgUserId;
        this.link = link;
        this.lastUpdate = lastUpdate;
    }
}
