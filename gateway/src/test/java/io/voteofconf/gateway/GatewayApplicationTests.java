package io.voteofconf.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GatewayApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void contextLoads() {
        assert (webTestClient != null);
//        webTestClient
//                .get().uri("/frontend") // GET method and URI
//                .accept(MediaType.TEXT_PLAIN) //setting ACCEPT-Content
//                .exchange() //gives access to response
//                .expectStatus().isOk() //checking if response is OK
//                .expectBody(String.class).isEqualTo("Hello World!"); // checking
    }

}
