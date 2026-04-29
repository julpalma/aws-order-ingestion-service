package com.julpalma.aws.orders.service;

import com.julpalma.aws.orders.config.AwsProperties;
import com.julpalma.aws.orders.entity.Order;
import com.julpalma.aws.orders.enums.OrderStatus;
import com.julpalma.aws.orders.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SqsOrderConsumer {

    private final AwsProperties awsProperties;
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    //polling frequency: every 5 seconds
    @Scheduled(fixedDelay = 5000)
    public void pollMessages() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(awsProperties.getSqs().getQueueUrl())
                .maxNumberOfMessages(5)
                .waitTimeSeconds(5)
                .build();

       // Every 5 seconds:
       //- call SQS receiveMessage
       //- wait up to 5 seconds for messages
       //- return up to 5 messages
        List<Message> messages = sqsClient.receiveMessage(request).messages();

        log.info("Received {} messages from SQS", messages.size());

        for (Message message : messages) {
            try {
                OrderEvent event = objectMapper.readValue(message.body(), OrderEvent.class);

                log.info("Processing order event: {}", event);
                processOrder(event);

                deleteMessage(message);

            } catch (Exception e) {
                log.error("Error processing message", e);
            }
        }

    }

    private void processOrder(OrderEvent event) {
        Order order = orderService.getOrderById(event.getOrderId());

        log.info("Processing order: {}", order);
        // simulate processing
        order.setOrderStatus(OrderStatus.PROCESSING);
        orderService.save(order);

        // simulate failure
        if ("FAIL".equalsIgnoreCase(order.getOrderType())) {
            throw new RuntimeException("Simulated failure");
        }

        order.setOrderStatus(OrderStatus.COMPLETED);
        orderService.save(order);
        log.info("Order processed successfully: {}", order);
    }

    private void deleteMessage(Message message) {
        sqsClient.deleteMessage(DeleteMessageRequest.builder()
                .queueUrl(awsProperties.getSqs().getQueueUrl())
                .receiptHandle(message.receiptHandle())
                .build());
        log.info("Deleted message from SQS: {}", message.messageId());
    }
}
