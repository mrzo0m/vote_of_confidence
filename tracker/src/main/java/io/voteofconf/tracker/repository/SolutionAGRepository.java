package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Solution;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SolutionAGRepository extends ReactiveCrudRepository<Solution, Long> {
}
