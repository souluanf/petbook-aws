package dev.luanfernandes.messaging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.luanfernandes.config.aws.AwsAppProperties;
import dev.luanfernandes.domain.enums.PersonEventType;
import dev.luanfernandes.service.EmailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SqsEmailConsumerService {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final AwsAppProperties awsProperties;

    @Scheduled(fixedDelay = 5000)
    public void consumeMessages() {
        String queueUrl = awsProperties.getSqs().getQueue().getEmailCreated().getUrl();

        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(1)
                .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();
        for (Message msg : messages) {
            try {
                String rawBody = msg.body();
                String snsPayload =
                        objectMapper.readTree(rawBody).get("Message").asText();
                JsonNode node = objectMapper.readTree(snsPayload);

                String email = node.get("email").asText();
                String name = node.get("name").asText();
                String type = node.get("type").asText();

                PersonEventType eventType;
                try {
                    eventType = PersonEventType.valueOf(type);
                } catch (IllegalArgumentException ex) {
                    log.warn("Tipo de evento desconhecido: {}", type);
                    eventType = null;
                }

                String subject = (eventType != null) ? eventType.getSubject() : "Notificação";

                String body =
                        "Olá " + name + ", este é um aviso sobre: " + (eventType != null ? eventType.name() : type);

                emailService.enviarEmail(email, subject, body);
                log.info("Email enviado com sucesso para {} (evento={})", email, type);

                sqsClient.deleteMessage(DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(msg.receiptHandle())
                        .build());

            } catch (Exception e) {
                log.error("Erro ao processar mensagem da fila SQS", e);
            }
        }
    }
}
