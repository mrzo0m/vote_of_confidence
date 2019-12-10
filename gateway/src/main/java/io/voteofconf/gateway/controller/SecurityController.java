package io.voteofconf.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.WebFilterChainProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private WebFilterChainProxy webFilterChainProxy;

    @GetMapping("/getFilterChainFirst")
    public Mono<WebFilterChainProxy> getFilterChainFirst() {
        return Mono.just(webFilterChainProxy);
    }

}
