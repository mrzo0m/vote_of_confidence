package io.voteofconf.tracker.controller;

import io.voteofconf.common.model.*;
import io.voteofconf.tracker.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = CommonController.class, excludeAutoConfiguration = { ReactiveSecurityAutoConfiguration.class, ReactiveUserDetailsServiceAutoConfiguration.class })
@ActiveProfiles("test")
@Slf4j
public class CommonControllerTest {

    private static final User USER = new User(
            null,
            User.ClientType.CANDIDATE,
            new AccountType(),
            "first",
            "second",
            "sur",
            "a@b.com",
            true,
            new HashSet<>()
    );

    private static final Company COMPANY = new Company(
            null,
            "Bubble Investing LTD",
            "Nothing",
            new HashSet<>(Collections.singletonList(USER)),
            new HashSet<>()
    );

    private static final Interview INTERVIEW = new Interview(
            0L,
            null,
            null,
            null,
            null,
            null,
            "http://path/to"
    );

    private static final Solution SOLUTION = new Solution(
            0L,
            Solution.ResolutionType.SUCCESS,
            null,
            null,
            null
    );

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserMWRepository userMWRepository;

    @MockBean
    private UserAGCrudRepository userAGRepository;

    @MockBean
    private CompanyMWRepository companyMWRepository;

    @MockBean
    private InterviewMWRepository interviewMWRepository;

    @MockBean
    private SolutionMWRepository solutionMWRepository;


    @Test
    public void getUserByEmailAddrTest() {
        doReturn(Flux.just(USER))
                .when(userAGRepository).findByEmailAddr("a@b.com");


        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getUserByEmailAddr")
                        .queryParam("emailAddr", "a@b.com")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .isEqualTo(Collections.singletonList(USER));
    }

    @Test
    public void getCandidatesTest() {
        doReturn(Flux.just(USER))
                .when(userAGRepository).findAllCandidates();


        webTestClient.get()
                .uri("/getCandidates")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .isEqualTo(Collections.singletonList(USER));
    }

    @Test
    public void getCandidatesByExpertise() {
        doReturn(Flux.just(USER))
                .when(userMWRepository).findAllCandidatesByExpertise(new HashSet<>(Arrays.asList("java", "j2ee")));


        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getCandidatesByExpertise")
                        .queryParam("keywords", "java", "j2ee")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .isEqualTo(Collections.singletonList(USER));
    }

    @Test
    public void getCandidatesByCompanyNameTest() {
        doReturn(Flux.just(USER))
                .when(userAGRepository).findCandidatesByCompanyName("Bubble investing LTD");


        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getCandidatesByCompanyName")
                        .queryParam("companyName", "Bubble investing LTD")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .isEqualTo(Collections.singletonList(USER));
    }

    @Test
    public void getExpertsByExpertiseTest() {
        doReturn(Flux.just(USER))
                .when(userMWRepository).findAllExpertsByExpertise(new HashSet<>(Arrays.asList("java", "j2ee")));


        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getExpertsByExpertise")
                        .queryParam("keywords", "java", "j2ee")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .isEqualTo(Collections.singletonList(USER));
    }

    @Test
    public void saveUserTest() {
        doReturn(Mono.just(USER))
                .when(userMWRepository).save(USER);

        webTestClient.post()
                .uri("/saveUser")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(USER)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(User.class)
                .isEqualTo(USER);
    }

    @Test
    public void deleteUserTest() {
        doReturn(Mono.empty())
                .when(userMWRepository).delete(0L);

        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/deleteUser")
                        .queryParam("userId", 0L)
                        .build())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void getCompanyByNameTest() {
        doReturn(Mono.just(COMPANY))
                .when(companyMWRepository).getCompanyByName("Bubble investing LTD");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getCompanyByName")
                        .queryParam("name", "Bubble investing LTD")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Company.class)
                .isEqualTo(Collections.singletonList(COMPANY));
    }

    @Test
    public void getInterviewTest() {
        doReturn(Mono.just(INTERVIEW))
                .when(interviewMWRepository).findById(INTERVIEW.getId());

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getInterview")
                        .queryParam("interviewId", INTERVIEW.getId())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Interview.class)
                .isEqualTo(INTERVIEW);
    }

    @Test
    public void getSolutionTest() {
        doReturn(Mono.just(SOLUTION))
                .when(solutionMWRepository).findById(SOLUTION.getId());


        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getSolution")
                        .queryParam("solutionId", SOLUTION.getId())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Solution.class)
                .isEqualTo(SOLUTION);
    }
}
