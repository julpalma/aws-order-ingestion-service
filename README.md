# 📦 aws-orders — Event-Driven Order Processing System (AWS + SQS + Spring Boot)

## 🚀 Overview

aws-orders is a Spring Boot microservice that implements an event-driven order processing system using AWS SQS (LocalStack for local development).

When an order is created via REST API, it is:

1. Persisted in a relational database
2. Published as an event to SQS
3. Consumed asynchronously by a scheduled worker
4. Processed and updated in the database

## 🧱 Architecture

Client (curl / Postman)
↓
Spring Boot REST API
↓
Order Service (DB persistence)
↓
SQS Publisher Service
↓
AWS SQS (LocalStack)
↓
Scheduled Consumer (@Scheduled polling)
↓
Order Processing Service
↓
Database update (status lifecycle)

## ⚙️ Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- AWS SQS (LocalStack for local dev)
- Docker (LocalStack)
- Jackson (JSON serialization)
- Maven
- MySQL

## 📌 Features

- REST API for order creation
- Input validation using Bean Validation (@Valid)
- Asynchronous event publishing via SQS
- Scheduled SQS polling consumer (@Scheduled)
- Order lifecycle management:
- RECEIVED → PROCESSING → COMPLETED
- Message deletion after successful processing
- Local AWS simulation using LocalStack

## 🔁 Order Processing Flow

1. Order created via REST API
2. Saved in database with status RECEIVED
3. Event sent to SQS
4. Consumer polls queue every 5 seconds
5. Message is deserialized into OrderEvent
6. Order is updated to:
   PROCESSING -> then COMPLETED
7. Message is deleted from SQS

## 🧪 How to Run Locally

1. Start docker:
   ```bash
   docker-compose up -d
   ```
2. Run Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
3. Create SQS Queue
   ```bash 
   aws --endpoint-url=http://localhost:4566 sqs create-queue \
      --queue-name orders-queue
   ```
4. Test API
   ```bash 
   curl -X POST http://localhost:8080/api/orders \
   -H "Content-Type: application/json" \
   -d '{
   "customerName": "Juliana",
   "orderType": "ONLINE",
   "amount": 99.99
   }'
   ```
5. Get order created
    ```bash 
    curl http://localhost:8080/api/orders/1
    ```
6. Check application logs to see:

- Message sent to SQS
- Message received by consumer
- Order processed successfully
- Message deleted from queue
