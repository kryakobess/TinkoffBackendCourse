package bot.services;

import bot.DTOs.requests.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@RabbitListener(queues = "${app.scrapperQueue.name}")
public class ScrapperQueueListener {

    final UpdateHandler handler;

    @RabbitHandler
    public void consumer(LinkUpdateRequest update){
        log.info("received new update from rabbitMQ");
        handler.handle(update);
    }
}
