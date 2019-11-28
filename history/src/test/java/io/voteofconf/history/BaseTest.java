package io.voteofconf.history;

import io.restassured.RestAssured;
import io.voteofconf.history.controller.BacklogController;
import io.voteofconf.history.dao.Backlog;
import io.voteofconf.history.dao.BacklogRebository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;


@Import(BacklogController.class)
@SpringBootTest(properties = "server.port=0")
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "test")
@WithMockUser
public class BaseTest {

    @LocalServerPort
    private int port;

    @MockBean
    private BacklogRebository backlogRebository;

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost:" + this.port;
        Backlog backlog = new Backlog();
        backlog.setInviteName("Oleg");
        Mockito
                .when(this.backlogRebository.findAll())
                .thenReturn(Flux.just(backlog));
    }
}
