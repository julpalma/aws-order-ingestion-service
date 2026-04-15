package com.julpalma.was_orders.controller;

import com.julpalma.was_orders.dto.CreateOrderRequest;
import com.julpalma.was_orders.entity.Order;
import com.julpalma.was_orders.enums.OrderStatus;
import com.julpalma.was_orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for handling HTTP requests related to Order resources.
 *
 * The @RestController annotation is a specialized version of @Controller that
 * combines @Controller and @ResponseBody, meaning that all methods return data
 * directly in the HTTP response body (typically JSON).
 *
 * This class defines API endpoints for creating and retrieving orders.
 * It acts as the entry point for client requests and delegates business logic
 * to the service layer.
 *
 * The controller follows REST principles and uses HTTP methods such as POST
 * and GET to perform operations on Order resources.
 */

@RequestMapping("/api/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order createOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createOrder);
    }

    //Example: curl http://localhost:8080/api/orders/1
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);

        return ResponseEntity.ok(order);
    }

    //Example: curl "http://localhost:8080/api/orders?status=RECEIVED"
    @GetMapping
    public ResponseEntity<List<Order>> getOrderByStatus(@RequestParam("status") OrderStatus orderStatus) {
        List<Order> orders = orderService.getOrderByStatus(orderStatus);

        return ResponseEntity.ok(orders);
    }
}
