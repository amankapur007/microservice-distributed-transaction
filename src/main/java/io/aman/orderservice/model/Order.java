package io.aman.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Order {

    @Id
    private String id;
    private Long productId;
    private Long quantity;
    private BigDecimal value;

    private OrderStatus status;

    public enum OrderStatus{
        NEW, DONE, CANCELED
    }
}
