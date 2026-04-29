package com.julpalma.was_orders.event;

import com.julpalma.was_orders.entity.Order;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {

    private Long orderId;
    private String customerName;
    private String orderType;
    private BigDecimal amount;
    private String status;

    public static OrderEvent from(Order order) {
        return OrderEvent.builder()
                .orderId(order.getId())
                .customerName(order.getCustomerName())
                .orderType(order.getOrderType())
                .amount(order.getAmount())
                .status(order.getOrderStatus().name())
                .build();
    }


}
