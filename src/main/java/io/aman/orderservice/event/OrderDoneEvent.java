package io.aman.orderservice.event;


import io.aman.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderDoneEvent {
    private String transactionId;
    private Order  order;
}