package io.voteofconf.history.statemachine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.Optional;

@Slf4j
public class StateMachineLogListener extends StateMachineListenerAdapter<States, Events> {
    @Override
    public void eventNotAccepted(Message<Events> event) {
        log.error("Not accepted event: {}", event);
    }

    @Override
    public void transition(Transition<States, Events> transition) {
        log.warn("MOVE from: {}, to: {}",
                ofNullableState(transition.getSource()),
                ofNullableState(transition.getTarget()));
    }

    private Object ofNullableState(State s) {
        return Optional.ofNullable(s)
                .map(State::getId)
                .orElse(null);

    }
}