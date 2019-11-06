package io.voteofconf.history.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.voteofconf.history.controller.dto.WebhookSubscription;
import io.voteofconf.history.dao.WebhookRepository;
import io.voteofconf.history.statemachine.Events;
import io.voteofconf.history.statemachine.States;
import io.voteofconf.history.statemachine.Variables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("webhooks")
@WithStateMachine
public class WebhooksController {

    @Autowired
    WebhookRepository repository;

    @Autowired
    ObjectMapper mapper;


    @Autowired
    StateMachine<States, Events> myStateMachine;

    @ApiOperation(value = "Post to invitee_created")
    @PostMapping("/invitee_created")
    @ResponseStatus(HttpStatus.OK)
    public void inviteeCreated(@RequestBody WebhookSubscription invitee) throws JsonProcessingException {
        log.info("webhooks event while invitee_created, palyload is: {}", invitee.toString());
        Message<Events> event = MessageBuilder
                .withPayload(Events.STORE)
                .setHeader(Variables.INVITE.toString(), invitee)
                .setHeader(Variables.REPOSITORY.toString(), repository)
                .setHeader(Variables.MAPPER.toString(), mapper)
                .build();
        myStateMachine.sendEvent(
                Mono.just(event))
                .doOnComplete(() -> {
                    System.out.println("Event handling complete");
                    Message<Events> addTaskEvent = MessageBuilder
                            .withPayload(Events.ADD_TASK).build();
                    myStateMachine
                            .sendEvent(
                                    Mono.just(addTaskEvent))
                            .subscribe();
                })
                .doOnError(Throwable::printStackTrace).subscribe();


    }

    @ApiOperation(value = "Post to invitee_canceled")
    @PostMapping("/invitee_canceled")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> inviteeCanceled(@RequestBody WebhookSubscription invitee) {
        log.info("webhooks event while invitee_canceled");
        return Mono.empty();
    }
}
