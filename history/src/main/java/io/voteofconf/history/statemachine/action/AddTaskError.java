package io.voteofconf.history.statemachine.action;

import io.voteofconf.history.statemachine.Events;
import io.voteofconf.history.statemachine.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddTaskError implements Action<States, Events> {


    @Override
    public void execute(StateContext<States, Events> context) {
        log.warn("Возникла ошибка на этапе добавления задачи в бэклог.... что то делаем");
    }


}
