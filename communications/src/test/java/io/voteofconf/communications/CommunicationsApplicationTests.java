package io.voteofconf.communications;

import io.voteofconf.communications.client.BacklogClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
@ActiveProfiles(value = "test")
public class CommunicationsApplicationTests {

    @Autowired
    private BacklogClient backlogClient;


    @Test
    public void contextLoads() {

        StepVerifier
                .create(this.backlogClient.getAllBacklog())
                .expectNextMatches(Predicate.isEqual(null))
                .verifyComplete();
    }

}
