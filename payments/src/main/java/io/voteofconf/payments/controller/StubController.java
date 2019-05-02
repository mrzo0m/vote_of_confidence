package io.voteofconf.payments.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/payments")
public class StubController {

    @RequestMapping(value = "/payAndGet", method = GET)
    @PreAuthorize("hasAuthority('SCOPE_email')")
    public Mono<OAuth2AuthenticationToken> payAndGet(Mono<OAuth2AuthenticationToken> token) {
        return token;
    }

    @RequestMapping(value = "/freeCharge", method = GET)
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public Mono<OAuth2AuthenticationToken> freeCharge(Mono<OAuth2AuthenticationToken> token) {
        return token;
    }
}
