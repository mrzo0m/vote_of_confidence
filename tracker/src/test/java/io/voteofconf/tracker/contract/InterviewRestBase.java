package io.voteofconf.tracker.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import io.voteofconf.common.model.Interview;
import io.voteofconf.tracker.controller.InterviewController;
import io.voteofconf.tracker.dto.ExpertTimes;
import io.voteofconf.tracker.repository.api.InterviewMWRepository;
import org.junit.Before;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class InterviewRestBase {

    private Interview interview;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    {
       interview = new Interview(0L, null, null, null, null, null,  "http:calendly.com/path/to");
    }

    @Before
    public void setup() {
        //remove::start[]
        RestAssuredWebTestClient.standaloneSetup(new InterviewController(
                new InterviewMWRepository() {
                    @Override
                    public Mono<Interview> save(Interview interview) {
                        interview.setId(0L);
                        return Mono.just(interview);
                    }

                    @Override
                    public Mono<Void> delete(Long interviewId) {
                        return Mono.empty();
                    }

                    @Override
                    public Flux<LocalDateTime> getAvailableTimes(ExpertTimes expertTimes) {
                        return Flux.just(LocalDateTime.parse("2019-12-01T01:00:00", formatter));
                    }

                    @Override
                    public Mono<Interview> findById(Long interviewId) {
                        return Mono.just(interview);
                    }
                }
        ));
        // remove::end[]
    }
}
