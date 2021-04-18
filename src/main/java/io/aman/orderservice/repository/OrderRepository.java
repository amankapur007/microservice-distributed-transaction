package io.aman.orderservice.repository;

import io.aman.orderservice.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

public interface OrderRepository extends ReactiveMongoRepository<Order, String> {
}
