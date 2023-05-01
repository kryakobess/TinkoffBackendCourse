package scrapper.services;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import scrapper.DTOs.requests.TgBotLinkUpdateRequest;
import scrapper.configuration.ApplicationConfig;

@RequiredArgsConstructor
public class ScrapperQueueProducer implements UpdateSender{

    final RabbitTemplate template;
    final ApplicationConfig appConfig;

    @Override
    public void send(TgBotLinkUpdateRequest linkUpdate){
        template.convertAndSend(appConfig.directExchangeName(), appConfig.routingKeyName(), linkUpdate);
    }
}
