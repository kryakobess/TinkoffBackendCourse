package scrapper.configuration.accessTypes;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scrapper.Repositories.jdbc.JdbcLinkDao;
import scrapper.Repositories.jdbc.JdbcTelegramUserDao;
import scrapper.services.LinkService;
import scrapper.services.TgUserService;
import scrapper.services.jdbc.JdbcLinkService;
import scrapper.services.jdbc.JdbcTgUserService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {

    @Bean
    LinkService jdbcLinkService(JdbcLinkDao linkDao, JdbcTelegramUserDao userDao){
        return new JdbcLinkService(linkDao, userDao);
    }

    @Bean
    TgUserService jdbcUserService(JdbcTelegramUserDao userDao, JdbcLinkDao linkDao){
        return new JdbcTgUserService(userDao, linkDao);
    }
}
