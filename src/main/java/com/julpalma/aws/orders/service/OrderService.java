package com.julpalma.aws.orders.service;

import com.julpalma.aws.orders.dto.CreateOrderRequest;
import com.julpalma.aws.orders.entity.Order;
import com.julpalma.aws.orders.enums.OrderStatus;
import com.julpalma.aws.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//Constructor Injection:
//RequiredArgsConstructor => Lombok annotation automatically creates a constructor
//Required creates a constructor for all final fields

//Constructor injection is preferred because it makes dependencies explicit,
//ensures immutability, improves testability, and prevents null-related issues.
//With Lombok’s @RequiredArgsConstructor, it keeps the code clean and concise.

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final SqsPublisherService sqsPublisherService;

    public Order createOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .orderType(request.getOrderType())
                .orderStatus(OrderStatus.RECEIVED)
                .amount(request.getAmount())
                .build();

        Order savedOrder = orderRepository.save(order);

        sqsPublisherService.sendOrderMessage(savedOrder);

        return savedOrder;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with id: " + id));
    }

    public List<Order> getOrderByStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

}
