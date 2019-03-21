package io.voteofconf.history.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("claim")
public class ClaimController {
    @GetMapping("hi")
    public Mono<String> getHi() {
        return Mono.just("hi for everyone");
    }

}
