package scrapper.configuration;

import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import scrapper.services.BotClientUpdateSender;
import scrapper.services.ScrapperQueueProducer;
import scrapper.services.TelegramBotClient;
import scrapper.services.UpdateSender;

@Configuration
@EnableScheduling
public class ScrapperConfiguration {
    @Bean
    public String schedulerIntervalMs(ApplicationConfig config) {
        return String.valueOf(config.scheduler().interval().toMillis());
    }

    @Bean
    public DefaultConfigurationCustomizer postgresJooqCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
                .withRenderSchema(false)
                .withRenderFormatted(true)
                .withRenderQuotedNames(RenderQuotedNames.NEVER);
    }

    @Bean
    public UpdateSender updateSender(ApplicationConfig appConfig, RabbitTemplate template,
                                     TelegramBotClient botClient) {
        if (appConfig.useQueue()) {
            return new ScrapperQueueProducer(template, appConfig);
        } else {
            return new BotClientUpdateSender(botClient);
        }
    }
}
