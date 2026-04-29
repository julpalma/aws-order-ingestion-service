package com.julpalma.aws.orders.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

//AWS Config Structure:
//aws
// ├── region
// ├── s3
// │     └── bucket
// └── sqs
//       └── queue-url

//Nested classes are used to represent hierarchical configuration in a clean and structured way.

@Getter
@Setter
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private String region;
    private S3Properties s3 = new S3Properties();
    private SqsProperties sqs = new SqsProperties();

    //static nested classes don’t need access to outer class instance
    //standard practice for config classes
    @Getter
    @Setter
    public static class S3Properties {
        private String bucket;
    }

    @Getter
    @Setter
    public static class SqsProperties {
        private String queueUrl;
    }
}
