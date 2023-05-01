package bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull String test,
        String scrapperBaseUrl,
        ScrapperRabbitMQ scrapperRabbitMQ,
        boolean useQueue
) {
        public record ScrapperRabbitMQ(String queue, String directExchange, String RoutingKey){}
}