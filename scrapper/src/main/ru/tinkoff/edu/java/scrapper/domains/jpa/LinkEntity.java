package scrapper.domains.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "link_subscription")
public class LinkEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tguserid")
    private TelegramUserEntity tgUserId;

    @Column(name = "link")
    String link;

    @Column(name = "lastupdate")
    Timestamp lastUpdate;
}
