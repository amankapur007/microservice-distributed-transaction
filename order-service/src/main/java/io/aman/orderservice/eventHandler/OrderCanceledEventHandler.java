package io.aman.orderservice.eventHandler;

import io.aman.orderservice.event.OrderCanceledEvent;
import io.aman.orderservice.model.Order;
import io.aman.orderservice.service.OrderService;
import io.aman.orderservice.util.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class OrderCanceledEventHandler {
    private  final OrderService service;
    private  final Converter converter;

    @RabbitListener(queues = {"${queue.order-canceled}"})
    public void onOrderCanceled(@Payload String payload){
        log.debug("Handling a refund order event {}", payload);
        OrderCanceledEvent event = converter.toObject(payload, OrderCanceledEvent.class);
        service.cancelOrder(event.getOrder().getId());
    }
}
