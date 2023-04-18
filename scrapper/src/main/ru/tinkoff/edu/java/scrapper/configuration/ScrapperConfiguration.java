package scrapper.configuration;

import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ScrapperConfiguration {
    @Bean
    public String schedulerIntervalMs(ApplicationConfig config) {
        return String.valueOf(config.scheduler().interval().toMillis());
    }

    @Bean
    public DefaultConfigurationCustomizer postgresJooqCustomizer(){
        return (DefaultConfiguration c) -> c.settings()
                .withRenderSchema(false)
                .withRenderFormatted(true)
                .withRenderQuotedNames(RenderQuotedNames.NEVER);
    }

}
