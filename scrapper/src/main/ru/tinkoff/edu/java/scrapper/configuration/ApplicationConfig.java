package scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)

@EnableScheduling
public record ApplicationConfig(
        @NotNull
        String test,
        @NotNull
        Scheduler scheduler,
        String gitHubBaseURL,
        String stackOverflowBaseURL,
        String telegramBotBaseURL) {
        
    record Scheduler(Duration interval){};

}
