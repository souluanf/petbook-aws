package dev.luanfernandes.config.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final OpenCepProperties openCepProperties;

    @Bean
    public RestClient openCepRestClient() {
        return RestClient.builder().baseUrl(openCepProperties.getUrl()).build();
    }
}
