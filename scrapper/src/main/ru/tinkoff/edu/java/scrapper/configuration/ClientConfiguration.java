package scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import scrapper.services.GitHubClient;
import scrapper.services.StackOverflowClient;

@Configuration
public class ClientConfiguration {
    @Bean
    GitHubClient gitHubClient(){
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.github.com")
                .build();
        HttpServiceProxyFactory factory =  HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return factory.createClient(GitHubClient.class);
    }

    @Bean
    StackOverflowClient stackOverflowClient(){
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.stackexchange.com")
                .build();
        HttpServiceProxyFactory factory =  HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return factory.createClient(StackOverflowClient.class);
    }
}
