package scrapper.configuration.accessTypes;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scrapper.Repositories.jooq.JooqLinkRepository;
import scrapper.Repositories.jooq.JooqTelegramUserRepository;
import scrapper.services.LinkService;
import scrapper.services.TgUserService;
import scrapper.services.jooq.JooqLinkService;
import scrapper.services.jooq.JooqTgUserService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    LinkService jooqLinkService(JooqLinkRepository linkRepository, JooqTelegramUserRepository userRepository){
        return new JooqLinkService(linkRepository, userRepository);
    }

    @Bean
    TgUserService jooqUserService(JooqTelegramUserRepository userRepository){
        return new JooqTgUserService(userRepository);
    }
}
