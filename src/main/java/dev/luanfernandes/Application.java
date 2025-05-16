package dev.luanfernandes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "dev.luanfernandes")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
