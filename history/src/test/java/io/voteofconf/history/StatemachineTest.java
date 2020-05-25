package io.voteofconf.history;

import io.voteofconf.history.config.HistoryStateMachineConfig;
import io.voteofconf.history.statemachine.Events;
import io.voteofconf.history.statemachine.States;
import io.voteofconf.history.statemachine.action.Store;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {HistoryStateMachineConfig.class, Store.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StatemachineTest {

    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;

    private StateMachine<States, Events> stateMachine;

    @BeforeEach
    public void setup() throws Exception {
        stateMachine = stateMachineFactory.getStateMachine();
        // plan don't know how to wait if machine is started
        // automatically so wait here.
        for (int i = 0; i < 10; i++) {
            if (stateMachine.getState() != null) {
                break;
            } else {
                Thread.sleep(300);
            }
        }
    }


    @Test
    @Ignore
    public void testInitial() throws Exception {
        Map<String, Object> clients = new HashMap<>();
        clients.put("client1", "client11");
        clients.put("client2", "client22");
        MessageHeaders headers = new MessageHeaders(clients);
        Message<Events> message = new GenericMessage<>(Events.STORE, headers);
        StateMachineTestPlan<States, Events> plan =
                StateMachineTestPlanBuilder.<States, Events>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState(States.INVITE)
                        .and()
                        .step()
                        .sendEvent(message)
                        .and()
                        .build();
        plan.test();
    }


    @Test
    @Ignore
    public void testStore() throws Exception {
        StateMachineTestPlan<States, Events> plan =
                StateMachineTestPlanBuilder.<States, Events>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState(States.INVITE)
                        .and()
                        .step()
                        .sendEvent(Events.STORE)
                        .expectState(States.INVITE_HISTORY)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    @Ignore
    public void testAddTaskToBacklog() throws Exception {
        StateMachineTestPlan<States, Events> plan =
                StateMachineTestPlanBuilder.<States, Events>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .and()
                        .step()
                        .sendEvent(Events.STORE)
                        .and()
                        .step()
                        .sendEvent(Events.ADD_TASK)
                        .expectState(States.BACKLOG)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    @Ignore
    public void testSolution() throws Exception {
        StateMachineTestPlan<States, Events> plan =
                StateMachineTestPlanBuilder.<States, Events>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .and()
                        .step()
                        .sendEvent(Events.STORE)
                        .and()
                        .step()
                        .sendEvent(Events.ADD_TASK)
                        .and()
                        .step()
                        .sendEvent(Events.PLAN_INTERVIEW)
                        .and()
                        .step()
                        .sendEvent(Events.FINISH_INTERVIEW)
                        .expectState(States.SOLUTION)
                        .expectStateMachineStopped(1)
                        .and()
                        .build();
        plan.test();
    }


}


