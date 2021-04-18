package io.aman.orderservice.controller;

import io.aman.orderservice.model.Order;
import io.aman.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    @PostMapping
    public Mono<Order> create(@RequestBody Order order){
        log.debug("Creating a new {}", order);
        return service.createOrder(order);
    }

    @GetMapping
    public Flux<Order> getAll(){
        return service.findAll();
    }
}
