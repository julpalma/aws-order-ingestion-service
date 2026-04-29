package com.julpalma.aws.orders.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(AwsProperties.class)
public class AwsConfig {

    @Bean
    public S3Client s3Client(AwsProperties awsProperties) {
        return S3Client.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.of(awsProperties.getRegion()))
                .forcePathStyle(true)
                .build();
    }

    @Bean
    public SqsClient sqsClient(AwsProperties awsProperties) {
        return SqsClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.of(awsProperties.getRegion()))
                .build();
    }

}
