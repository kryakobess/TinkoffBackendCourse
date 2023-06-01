package scrapper.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
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
