package io.voteofconf.history.statemachine.action;

import io.voteofconf.history.statemachine.Events;
import io.voteofconf.history.statemachine.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
public class AddTask implements Action<States, Events> {
    @Override
    public void execute(StateContext<States, Events> context) {

    }
}
