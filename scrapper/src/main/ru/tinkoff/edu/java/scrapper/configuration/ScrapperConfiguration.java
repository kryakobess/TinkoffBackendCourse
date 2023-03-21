package scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScrapperConfiguration {
    @Bean
    public String schedulerIntervalMs(ApplicationConfig config) {
        return String.valueOf(config.scheduler().interval().toMillis());
    }
}
