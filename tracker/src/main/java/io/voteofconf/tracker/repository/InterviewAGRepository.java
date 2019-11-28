package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Interview;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface InterviewAGRepository extends ReactiveCrudRepository<Interview, Long> {
}
