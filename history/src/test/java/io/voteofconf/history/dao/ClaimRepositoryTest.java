package io.voteofconf.history.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;

@RunWith(SpringRunner.class)
public class ClaimRepositoryTest {


    @MockBean
    ClaimRepository claimRepository;

    @Test
    public void persist() throws Exception {
        Mockito.when(this.claimRepository.findAll())
                .thenReturn(
                        Flux.just(
                                new Claim(
                                        new ClaimKey("Company" + UUID.randomUUID(),
                                                UUID.randomUUID()),
                                        "info: " + System.nanoTime())
                        )
                );


        StepVerifier
                .create(this.claimRepository.findAll())
                .expectNextMatches(
                        claim ->
                                claim.getKey().getId() != null
                                        && claim.getKey().getCompanyName().contains("Company")
                ).verifyComplete();
    }

}