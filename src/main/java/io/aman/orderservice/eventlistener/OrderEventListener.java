package io.aman.orderservice.eventlistener;

import io.aman.orderservice.event.OrderCreatedEvent;
import io.aman.orderservice.util.Converter;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Log4j2
@Component
public class OrderEventListener {

    private  final RabbitTemplate rabbitTemplate;
    private  final Converter      converter;
    private  final String queueOrderCreateName;

    public OrderEventListener(RabbitTemplate rabbitTemplate, Converter converter,
                              @Value("${queue.order-created}") String queueOrderCreateName){
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
        this.queueOrderCreateName = queueOrderCreateName;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public  void onCreateEvent(OrderCreatedEvent event){
        log.debug("Sending order created event to {}, event: {}", queueOrderCreateName, event);
        rabbitTemplate.convertAndSend(queueOrderCreateName, converter.toJSON(event));
    }
}
