package bot.configuration;

import bot.DTOs.requests.LinkUpdateRequest;
import org.springframework.amqp.core.*;
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
    DirectExchange directExchange(ApplicationConfig appConfig){
        return new DirectExchange(appConfig.scrapperRabbitMQ().directExchange(), true, false);
    }

    @Bean
    Queue queue(ApplicationConfig appConfig){
        return QueueBuilder
                .durable(appConfig.scrapperRabbitMQ().queue())
                .build();
    }

    @Bean
    Binding binding(ApplicationConfig appConfig){
        return BindingBuilder
                .bind(queue(appConfig))
                .to(directExchange(appConfig))
                .with(appConfig.scrapperRabbitMQ().RoutingKey());

    }

    @Bean
    public ClassMapper classMapper(){
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("scrapper.DTOs.requests.TgBotLinkUpdateRequest", LinkUpdateRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("bot.DTOs.requests.LinkUpdateRequest");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper){
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }
}
