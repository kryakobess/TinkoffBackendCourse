package scrapper.configuration.accessTypes;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scrapper.Repositories.jpa.JpaLinkRepository;
import scrapper.Repositories.jpa.JpaTelegramUserRepository;
import scrapper.services.LinkService;
import scrapper.services.TgUserService;
import scrapper.services.jpa.JpaLinkService;
import scrapper.services.jpa.JpaTgUserService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    LinkService jpaLinkService(JpaLinkRepository linkRepository, JpaTelegramUserRepository userRepository) {
        return new JpaLinkService(userRepository, linkRepository);
    }

    @Bean
    TgUserService jpaUserService(JpaTelegramUserRepository userRepository) {
        return new JpaTgUserService(userRepository);
    }
}
