package dev.luanfernandes.webclient.opencep;

import dev.luanfernandes.config.rest.OpenCepProperties;
import dev.luanfernandes.webclient.opencep.response.OpenCepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class OpenCepClient {
    private final RestClient openCepRestClient;
    private final OpenCepProperties brasilApiProperties;

    public OpenCepResponse byPostalCode(String cep) {
        return openCepRestClient
                .get()
                .uri(brasilApiProperties.getRoute(), cep)
                .retrieve()
                .toEntity(OpenCepResponse.class)
                .getBody();
    }
}
