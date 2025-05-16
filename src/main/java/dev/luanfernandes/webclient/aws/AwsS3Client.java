package dev.luanfernandes.webclient.aws;

import dev.luanfernandes.config.aws.AwsAppProperties;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class AwsS3Client {

    private final S3Client s3Client;
    private final AwsAppProperties awsProperties;

    public void uploadFile(String key, MultipartFile file) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(awsProperties.getS3().getBucket())
                            .key(key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload para o S3", e);
        }
    }

    public byte[] downloadFile(String key) {
        ResponseBytes<GetObjectResponse> object = s3Client.getObjectAsBytes(GetObjectRequest.builder()
                .bucket(awsProperties.getS3().getBucket())
                .key(key)
                .build());
        return object.asByteArray();
    }
}
