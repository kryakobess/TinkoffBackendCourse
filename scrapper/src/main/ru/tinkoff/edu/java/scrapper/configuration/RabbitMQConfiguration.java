package scrapper.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    DirectExchange directExchange(ApplicationConfig appConfig) {
        return new DirectExchange(appConfig.directExchangeName(), true, false);
    }

    @Bean
    Queue queue(ApplicationConfig appConfig) {
        return QueueBuilder
                .durable(appConfig.queueName())
                .withArgument("x-dead-letter-exchange", appConfig.queueName() + ".dlx")
                .withArgument("x-dead-letter-routing-key", appConfig.queueName() + ".dlq")
                .build();
    }

    @Bean
    Binding binding(ApplicationConfig appConfig) {
        return BindingBuilder
                .bind(queue(appConfig))
                .to(directExchange(appConfig))
                .with(appConfig.routingKeyName());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
