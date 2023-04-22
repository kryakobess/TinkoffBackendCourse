package scrapper.domains.jpa;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tg_user")
public class TelegramUserEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    public TelegramUserEntity(Long chatId) {
        this.chatId = chatId;
    }
}
