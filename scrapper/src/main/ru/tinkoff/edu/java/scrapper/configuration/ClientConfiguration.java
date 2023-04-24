package scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import scrapper.services.GitHubClient;
import scrapper.services.StackOverflowClient;
import scrapper.services.TelegramBotClient;

import java.time.Duration;

@Configuration
public class ClientConfiguration {
    @Bean
    GitHubClient gitHubClient(ApplicationConfig appConfig){
        WebClient webClient = WebClient.builder()
                .baseUrl(appConfig.gitHubBaseURL().isBlank() ? "https://api.github.com" : appConfig.gitHubBaseURL())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1048576))
                .build();
        HttpServiceProxyFactory factory =  HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return factory.createClient(GitHubClient.class);
    }

    @Bean
    StackOverflowClient stackOverflowClient(ApplicationConfig appConfig){
        WebClient webClient = WebClient.builder()
                .baseUrl(appConfig.stackOverflowBaseURL().isBlank() ? "https://api.stackexchange.com" : appConfig.stackOverflowBaseURL())
                .build();
        HttpServiceProxyFactory factory =  HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).blockTimeout(Duration.ofSeconds(10, 10)).build();
        return factory.createClient(StackOverflowClient.class);
    }

    @Bean
    TelegramBotClient telegramBotClient(ApplicationConfig appConfig){
        WebClient webClient = WebClient.builder()
                .baseUrl(appConfig.telegramBotBaseURL().isBlank() ? "localhost:8081" : appConfig.telegramBotBaseURL())
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return factory.createClient(TelegramBotClient.class);
    }
}
