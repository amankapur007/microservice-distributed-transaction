package io.aman.orderservice.service;

import io.aman.orderservice.event.OrderCreatedEvent;
import io.aman.orderservice.model.Order;
import io.aman.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public Mono<Order> createOrder(Order order){
        order.setStatus(Order.OrderStatus.NEW);
        log.debug("Saving an order {} ", order);
        Mono<Order> returnOrder = repository.save(order);
        publish(order);
        return  returnOrder;
    }

    private void publish(Order order) {
        OrderCreatedEvent event = new OrderCreatedEvent(UUID.randomUUID().toString(), order);
        log.debug("Publishing an order created event {}", event);
        applicationEventPublisher.publishEvent(event);
    }

    public Flux <Order> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void updateOrderAsDone(String orderId){
        log.debug("Updating Order {} to {}", orderId, Order.OrderStatus.DONE);
        Mono<Order> orderUpdated = repository.findById(orderId);
        Order order = orderUpdated.block();
        if(order!=null){
            order.setStatus(Order.OrderStatus.DONE);
            repository.save(order);
            log.debug("Order {} done", order.getId());
        }else{
            log.error("Cannot update Order to status {}, Order {} not found", Order.OrderStatus.DONE, orderId);
        }
    }

    @Transactional
    public void cancelOrder(String orderId) {
        log.debug("Canceling Order {}", orderId);
        Mono<Order> orderCancel = repository.findById(orderId);
        Order order = orderCancel.block();
        if (order!=null) {
            order.setStatus(Order.OrderStatus.CANCELED);
            repository.save(order);
            log.debug("Order {} was canceled", order.getId());
        } else {
            log.error("Cannot find an Order {}", orderId);
        }
    }
}
