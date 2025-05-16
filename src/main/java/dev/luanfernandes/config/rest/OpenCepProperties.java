package dev.luanfernandes.config.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "external.apis.opencep")
public class OpenCepProperties {
    private String url;
    private String route;
}
