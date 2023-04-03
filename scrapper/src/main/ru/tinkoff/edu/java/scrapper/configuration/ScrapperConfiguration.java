package scrapper.configuration;

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

}
