package dev.luanfernandes.config.aws;

import io.awspring.cloud.autoconfigure.core.AwsProperties;
import io.awspring.cloud.autoconfigure.core.CredentialsProperties;
import io.awspring.cloud.autoconfigure.core.RegionProperties;
import java.net.URI;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    private final AwsProperties awsProperties;
    private final AwsAppProperties awsAppProperties;
    private final RegionProperties regionProperties;
    private final CredentialsProperties credentialsProperties;

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .endpointOverride(URI.create(
                        awsAppProperties.getSqs().getQueue().getEmailCreated().getUrl()))
                .region(Region.of(Objects.requireNonNull(regionProperties.getStatic())))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                        credentialsProperties.getAccessKey(), credentialsProperties.getSecretKey())))
                .build();
    }

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .endpointOverride(awsProperties.getEndpoint())
                .region(Region.of(Objects.requireNonNull(regionProperties.getStatic())))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                        credentialsProperties.getAccessKey(), credentialsProperties.getSecretKey())))
                .build();
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(awsProperties.getEndpoint())
                .region(Region.of(Objects.requireNonNull(regionProperties.getStatic())))
                .forcePathStyle(true)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                        credentialsProperties.getAccessKey(), credentialsProperties.getSecretKey())))
                .build();
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(awsProperties.getEndpoint())
                .region(Region.of(Objects.requireNonNull(regionProperties.getStatic())))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                        credentialsProperties.getAccessKey(), credentialsProperties.getSecretKey())))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
    }
}
