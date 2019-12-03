package io.voteofconf.tracker.contract;

import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import io.voteofconf.common.model.Interview;
import io.voteofconf.tracker.controller.InterviewController;
import io.voteofconf.tracker.dto.ExpertTimes;
import io.voteofconf.tracker.repository.api.InterviewMWRepository;
import org.junit.Before;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class InterviewRestBase {

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
                        return Flux.empty();
                    }

                    @Override
                    public Mono<Interview> findById(Long interviewId) {
                        return Mono.empty();
                    }
                }
        ));
        // remove::end[]
    }
}
