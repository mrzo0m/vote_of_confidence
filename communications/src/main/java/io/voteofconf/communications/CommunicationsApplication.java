package io.voteofconf.communications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class CommunicationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunicationsApplication.class, args);
    }

}
