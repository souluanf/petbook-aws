package dev.luanfernandes.service.impl;

import dev.luanfernandes.config.aws.AwsAppProperties;
import dev.luanfernandes.domain.entity.Person;
import dev.luanfernandes.domain.enums.PersonEventType;
import dev.luanfernandes.service.SnsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
@RequiredArgsConstructor
public class SnsServiceImpl implements SnsService {

    private final SnsClient snsClient;
    private final AwsAppProperties awsProperties;

    @Override
    public void publishPersonEvent(PersonEventType type, Person person) {
        String message =
                """
                            {
                              "type": "%s",
                              "email": "%s",
                              "name": "%s"
                            }
                        """
                        .formatted(type.name(), person.getEmail(), person.getName());

        PublishRequest request = PublishRequest.builder()
                .topicArn(awsProperties.getSns().getTopic().getArn())
                .subject(type.getSubject())
                .message(message)
                .build();

        snsClient.publish(request);
    }
}
