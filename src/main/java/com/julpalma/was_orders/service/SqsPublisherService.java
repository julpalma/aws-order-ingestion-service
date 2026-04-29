package com.julpalma.was_orders.service;

import com.julpalma.was_orders.config.AwsProperties;
import com.julpalma.was_orders.entity.Order;
import com.julpalma.was_orders.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class SqsPublisherService {
    private final SqsClient sqsClient;
    private final AwsProperties awsProperties;
    private final ObjectMapper objectMapper;

    public void sendOrderMessage(Order order) {
        try {
            OrderEvent event = OrderEvent.from(order);

            String messageBody = objectMapper.writeValueAsString(event);

            log.info("Sending message to SQS: {}", messageBody);

            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(awsProperties.getSqs().getQueueUrl())
                    .messageBody(messageBody)
                    .build();

            sqsClient.sendMessage(request);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send message to SQS", e);
        }
    }

}
