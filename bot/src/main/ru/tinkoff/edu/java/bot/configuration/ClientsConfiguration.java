package bot.configuration;

import bot.services.ScrapperClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientsConfiguration {
    private final static String SCRAPPER_CLIENT_DEFAULT_URL = "http://localhost:8080";

    @Bean
    ScrapperClient scrapperClient(ApplicationConfig appConfig) {
        WebClient webClient = WebClient.builder()
                .baseUrl(appConfig.scrapperBaseUrl().isBlank()
                    ? SCRAPPER_CLIENT_DEFAULT_URL : appConfig.scrapperBaseUrl())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builder(WebClientAdapter.forClient(webClient))
            .build();
        return factory.createClient(ScrapperClient.class);
    }
}
