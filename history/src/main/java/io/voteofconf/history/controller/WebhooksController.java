package io.voteofconf.history.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import io.voteofconf.history.controller.dto.WebhookSubscription;
import io.voteofconf.history.service.CalendlyWebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("webhooks")
public class WebhooksController {

    @Autowired
    CalendlyWebhookService service;

    @ApiOperation(value = "Post to invitee_created")
    @PostMapping("/invitee_created")
    @ResponseStatus(HttpStatus.OK)
    public void inviteeCreated(@RequestBody WebhookSubscription invitee) {
        log.info("webhooks event while invitee_created, palyload is: {}", invitee.toString());
        service.inviteeCreated(invitee);
    }

    @ApiOperation(value = "Post to invitee_canceled")
    @PostMapping("/invitee_canceled")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> inviteeCanceled(@RequestBody WebhookSubscription invitee) {
        log.info("webhooks event while invitee_canceled");
        return Mono.empty();
    }

    @GetMapping("/websession")
    public Mono<String> getSession(WebSession session) {
        session.getAttributes().putIfAbsent("note", "Howdy Cosmic Spheroid!");
        return Mono.just((String) session.getAttributes().get("note"));
    }
}
