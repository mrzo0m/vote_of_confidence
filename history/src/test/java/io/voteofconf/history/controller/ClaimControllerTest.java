package io.voteofconf.history.controller;

import io.voteofconf.history.dao.Claim;
import io.voteofconf.history.dao.ClaimKey;
import io.voteofconf.history.dao.ClaimRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.UUID;


@RunWith(SpringRunner.class)
@WebFluxTest(controllers = ClaimController.class)
@Import(ClaimRepository.class)
@ActiveProfiles(value = "test")
@WithMockUser
public class ClaimControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private ClaimRepository claimRepository;


    @Test
    public void getAll() {
        Mockito.when(this.claimRepository.findAll())
                .thenReturn(
                        Flux.just(
                                new Claim(
                                        new ClaimKey("Company" + UUID.randomUUID(),
                                                UUID.randomUUID()),
                                        "info: " + System.nanoTime())
                        )
                );
        this.webTestClient
                .get()
                .uri("/claims")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody()
                .jsonPath("@.[0].claimInfo", "info: ")
                .exists();
    }

    @Test
    public void get() {
    }

    @Test
    public void put() {
    }

    @Test
    public void save() {
    }
}