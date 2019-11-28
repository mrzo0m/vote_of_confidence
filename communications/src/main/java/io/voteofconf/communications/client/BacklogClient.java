package io.voteofconf.communications.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class BacklogClient {


    private WebClient webClient;

    @Autowired
    public BacklogClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Backlog> getAllBacklog() {
        return this.webClient
                .get()
                .uri("http://localhost:8000/history-microservice")
                .retrieve()
                .bodyToFlux(Backlog.class);
    }
}
