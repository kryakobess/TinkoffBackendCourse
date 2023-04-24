package scrapper.domains;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class TelegramUser {
    Long id;
    Long chatId;

    public TelegramUser(Long chatId) {
        this.chatId = chatId;
    }
}
