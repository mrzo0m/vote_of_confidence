package io.voteofconf.history.statemachine.action;

import io.voteofconf.history.controller.dto.WebhookSubscription;
import io.voteofconf.history.statemachine.Events;
import io.voteofconf.history.statemachine.States;
import io.voteofconf.history.statemachine.Variables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;

@Slf4j
public class Store {
    public static void execute(StateContext<States, Events> stateContext) {
        WebhookSubscription subscription = (WebhookSubscription) stateContext.getMessageHeaders().get(Variables.INVITE.toString());
        log.warn("Бросаем в кассандру {}", subscription.getEvent());
    }
}
