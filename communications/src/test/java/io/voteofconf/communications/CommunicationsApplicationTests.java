package io.voteofconf.communications;

import io.voteofconf.communications.client.BacklogClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
@ActiveProfiles(value = "test")
@AutoConfigureWireMock(port = 8000)
public class CommunicationsApplicationTests {

    @Autowired
    private BacklogClient backlogClient;


    @Test
    public void contextLoads() {
        stubFor(get(
                urlEqualTo("/history-microservice"))
                .willReturn(
                        aResponse()
                                .withHeader(
                                        HttpHeaders.CONTENT_TYPE,
                                        MediaType.APPLICATION_JSON_VALUE)
                                .withBody("[{\"inviteName\":\"Oleg\"}]")
                                .withStatus(HttpStatus.OK.value())));

        StepVerifier
                .create(this.backlogClient.getAllBacklog())
                .expectNextMatches(backlog -> backlog != null)
                .verifyComplete();
    }

}
