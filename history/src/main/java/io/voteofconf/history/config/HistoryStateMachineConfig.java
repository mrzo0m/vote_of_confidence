package io.voteofconf.history.config;

import io.voteofconf.history.statemachine.Events;
import io.voteofconf.history.statemachine.StateMachineLogListener;
import io.voteofconf.history.statemachine.States;
import io.voteofconf.history.statemachine.action.AddTask;
import io.voteofconf.history.statemachine.action.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnableWithStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

@Slf4j
@Configuration
public class HistoryStateMachineConfig {
    @Bean
    public StateMachineLogListener stateMachineLogListener() {
        return new StateMachineLogListener();
    }


    @Configuration
    @EnableWithStateMachine
    @EnableStateMachineFactory
    public static class Config extends EnumStateMachineConfigurerAdapter<States, Events> {
        @Autowired
        private StateMachineLogListener stateMachineLogListener;

        @Autowired
        private Store store;

        @Autowired
        private AddTask addTask;

        private static void execute(StateContext<States, Events> stateContext) {
            log.warn("Бросаем в кассандру {}", stateContext.getMessageHeaders().get("client1"));
        }

        @Override
        public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
            config
                    .withConfiguration()
                    .autoStartup(true)
                    .listener(stateMachineLogListener);
        }

        @Override
        public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
            states.withStates()
                    .initial(States.INVITE, incomingInvite())
                    .state(States.INVITE_HISTORY, store)
                    .state(States.BACKLOG, addTask)
                    .state(States.INPROGRESS, changeStatusToInprogressAction())
                    .state(States.SOLUTION, handleSolution())
                    .end(States.SOLUTION);
        }

        @Bean
        public Action<States, Events> handleSolution() {
            return stateContext -> log.warn("Вызываем обработку решения");
        }

        @Bean
        public Action<States, Events> incomingInvite() {
            return stateContext -> log.warn("Календли прислал запрос на интервью");
        }

        @Bean
        public Action<States, Events> changeStatusToInprogressAction() {
            return stateContext -> log.warn("Эксперт взял таск из беклога");
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
            transitions.withExternal()
                    .source(States.INVITE)
                    .target(States.INVITE_HISTORY)
                    .event(Events.STORE)
                    .and()
                    .withExternal()
                    .source(States.INVITE_HISTORY)
                    .target(States.BACKLOG)
                    .event(Events.ADD_TASK)
                    .guard(alreadyInBacklogGuard())
                    .and()
                    .withExternal()
                    .source(States.BACKLOG)
                    .target(States.INPROGRESS)
                    .event(Events.PLAN_INTERVIEW)
                    .and()
                    .withExternal()
                    .source(States.INPROGRESS)
                    .target(States.BACKLOG)
                    .event(Events.INTERVIEWER_REJECT)
                    .and()
                    .withExternal()
                    .source(States.INPROGRESS)
                    .target(States.SOLUTION)
                    .event(Events.FINISH_INTERVIEW);
        }

        private Guard<States, Events> alreadyInBacklogGuard() {
            return context -> true;
//            return context -> Optional.ofNullable(context.getExtendedState().getVariables().get("deployed"))
//                    .map(v -> (boolean) v)
//                    .orElse(false);
        }

    }
}
