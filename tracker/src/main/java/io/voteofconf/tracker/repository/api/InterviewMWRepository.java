package io.voteofconf.tracker.repository.api;

import io.voteofconf.common.model.Interview;
import io.voteofconf.common.model.User;
import io.voteofconf.tracker.dto.ExpertTimes;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface InterviewMWRepository extends Repository<Interview> {

    Mono<Interview> findById(Long interviewId);

    Mono<Interview> save(Interview interview);

    Mono<Void> delete(Long interviewId);

    Flux<LocalDateTime> getAvailableTimes(ExpertTimes expertTimes);
}
