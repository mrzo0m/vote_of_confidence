package io.voteofconf.tracker;

import io.voteofconf.tracker.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TrackerApplication {

	public static void main(String[] args) {
		SpringApplication
				.run(TrackerApplication.class, args);
	}

}
