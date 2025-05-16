package dev.luanfernandes.config.web;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MultipartConfigProperties {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;
}
