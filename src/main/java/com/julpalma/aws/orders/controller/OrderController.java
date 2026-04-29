package com.julpalma.aws.orders.controller;

import com.julpalma.aws.orders.dto.CreateOrderRequest;
import com.julpalma.aws.orders.entity.Order;
import com.julpalma.aws.orders.enums.OrderStatus;
import com.julpalma.aws.orders.service.OrderService;
import com.julpalma.aws.orders.service.S3StorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final S3StorageService s3StorageService;

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

    //Example: curl http://localhost:8080/api/orders?status=RECEIVED
    @GetMapping
    public ResponseEntity<List<Order>> getOrderByStatus(@RequestParam("status") OrderStatus orderStatus) {
        List<Order> orders = orderService.getOrderByStatus(orderStatus);

        return ResponseEntity.ok(orders);
    }

    @PostMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Order> uploadOrderFile(@PathVariable Long id,
                                                 @RequestParam("file")MultipartFile file) throws IOException {
        Order order = orderService.getOrderById(id);

        String key = s3StorageService.uploadFile(id, file);

        order.setS3Key(key);
        Order updateOrder = orderService.save(order);

        return ResponseEntity.ok(updateOrder);
    }
}
