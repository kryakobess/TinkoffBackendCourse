package scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
        String telegramBotBaseURL,
        AccessType databaseAccessType,
        String directExchangeName,
        String queueName,
        String routingKeyName,
        boolean useQueue
        ) {

    record Scheduler(Duration interval){}

    enum AccessType {
        JDBC, JOOQ, JPA
    }

}
