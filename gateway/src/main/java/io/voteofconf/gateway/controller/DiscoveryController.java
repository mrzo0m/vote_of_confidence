package io.voteofconf.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("discovery")
public class DiscoveryController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("getServices")
    public Mono<String> getServices() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : discoveryClient.getServices()) {
            stringBuilder.append(s).append(" ,");
        }
        return Mono.just(stringBuilder.toString());
    }

    @GetMapping("getInfo")
    public Mono<String> getInfo() {
        return Mono.just(discoveryClient.description());
    }
}
