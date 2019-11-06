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
                .setHeader(Variables.INVITE.toString(), invitee).build();
        myStateMachine.sendEvent(Mono.just(event))
                .doOnComplete(() -> {
                    System.out.println("Event handling complete");
                })
                .doOnError(Throwable::printStackTrace).subscribe();



//        CalendlyInviteeKey key = new CalendlyInviteeKey();
//        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd", new Locale("ru"));
//        LocalDate date = LocalDate.parse(invitee.getTime(), inputFormatter);
//        String formattedDate = outputFormatter.format(date);
//        key.setInviteeEmail(invitee.getPayload().getInvitee().getEmail());
//        key.setDay(formattedDate);
//        int min = 1; //TODO: Кол-во узлов в кластере кассандры поулчать из конфигмапы куба
//        int max = 1;
//        Integer bucket = new Random().ints(min, (max + 1)).limit(1).findFirst().getAsInt();
//        key.setBucket(bucket);
//        key.setTs(UUIDs.timeBased());
//        CalendlyInvitee calendlyInvitee = new CalendlyInvitee();
//        calendlyInvitee.setCalendlyInviteeKey(key);
//        calendlyInvitee.setEvent(invitee.getEvent());
//        calendlyInvitee.setPayload(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(invitee.getPayload()));
//        return repository.save(calendlyInvitee);
    }

    @ApiOperation(value = "Post to invitee_canceled")
    @PostMapping("/invitee_canceled")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> inviteeCanceled(@RequestBody WebhookSubscription invitee) {
        log.info("webhooks event while invitee_canceled");
        return Mono.empty();
    }
}
