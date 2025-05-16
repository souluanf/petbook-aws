package dev.luanfernandes.config.aws;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.cloud.aws")
public class AwsAppProperties {
    private S3 s3;
    private Sns sns;
    private Sqs sqs;

    @Getter
    @Setter
    public static class Sqs {
        private Queue queue;
    }

    @Getter
    @Setter
    public static class Queue {
        private EmailCreated emailCreated;
    }

    @Getter
    @Setter
    public static class EmailCreated {
        private String url;
    }

    @Getter
    @Setter
    public static class S3 {
        private String bucket;
    }

    @Getter
    @Setter
    public static class Sns {
        private Topic topic;
    }

    @Getter
    @Setter
    public static class Topic {
        private String arn;
    }
}
