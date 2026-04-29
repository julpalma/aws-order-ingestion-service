package com.julpalma.aws.orders.repository;

import com.julpalma.aws.orders.entity.Order;
import com.julpalma.aws.orders.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Order entities.
 *
 * This interface extends JpaRepository, which provides built-in CRUD operations
 * such as save, findById, findAll, and delete.
 *
 * Spring Data JPA automatically generates the implementation at runtime,
 * so no manual implementation is required.
 *
 * Custom query methods can be defined by following Spring Data naming conventions.
 * For example, findByStatus allows retrieving orders filtered by their status.
 *
 * This repository acts as the data access layer between the application
 * and the PostgreSQL database.
 */


public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);

}
