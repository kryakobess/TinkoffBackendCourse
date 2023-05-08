package bot.configuration;

import bot.DTOs.requests.LinkUpdateRequest;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    DirectExchange deadLetterDirectExchange(ApplicationConfig appConfig) {
        return new DirectExchange(appConfig.scrapperRabbitMQ().queue() + ".dlx", true, false);
    }

    @Bean
    Queue deadLetterQueue(ApplicationConfig appConfig) {
        return new Queue(appConfig.scrapperRabbitMQ().queue() + ".dlq");
    }

    @Bean
    Binding deadLetterBinding(ApplicationConfig appConfig) {
        return BindingBuilder
                .bind(deadLetterQueue(appConfig))
                .to(deadLetterDirectExchange(appConfig))
                .withQueueName();
    }

    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("scrapper.DTOs.requests.TgBotLinkUpdateRequest", LinkUpdateRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("bot.DTOs.requests.LinkUpdateRequest");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }
}
