package io.voteofconf.communications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class CommunicationsApplication {

    public static void main(String[] args) {
        // Initialize Api Context for Telegram
        ApiContextInitializer.init();

        SpringApplication.run(CommunicationsApplication.class, args);
    }

}
