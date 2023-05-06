package scrapper.services;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scrapper.IntegrationEnvironment;
import scrapper.configuration.ApplicationConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdateSenderTest extends IntegrationEnvironment {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ApplicationConfig applicationConfig;
    @Test
    void send() {
        rabbitTemplate.convertAndSend(applicationConfig.queueName(), "hello world!");
    }
}