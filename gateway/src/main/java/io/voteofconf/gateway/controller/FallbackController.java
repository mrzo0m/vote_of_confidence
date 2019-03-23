package io.voteofconf.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("fallback")
public class FallbackController {

    @GetMapping("history")
    public Mono<String> getHistoryFallback() {
        return Mono.just("history service is temporarily unavailable. Please try again later!");
    }

    @GetMapping("frontend")
    public Mono<String> getFrontendFallback() {
        return Mono.just("Frontend service is temporarily unavailable. Please try again later!");
    }

}
