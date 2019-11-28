package io.voteofconf.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
//@WebFluxTest
@OverrideAutoConfiguration(enabled = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@WithMockUser
@AutoConfigureWireMock(port = 0)
public class GatewayApplicationTests {


    @Test
    public void contextLoads() {
        stubFor(get(
                urlEqualTo("/history-microservice"))
                .willReturn(
                        aResponse()
                                .withHeader(
                                        HttpHeaders.CONTENT_TYPE,
                                        MediaType.APPLICATION_JSON_VALUE)
                                .withBody("body")
                                .withStatus(HttpStatus.OK.value())));


    }

}
