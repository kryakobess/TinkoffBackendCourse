package scrapper.domains;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TelegramUser {
    Long id;
    Long chatId;
    String userName;
}
