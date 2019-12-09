package io.voteofconf.tracker.repository.api;

import io.voteofconf.common.model.Interview;
import io.voteofconf.common.model.Solution;
import reactor.core.publisher.Mono;

public interface SolutionMWRepository extends Repository<Solution> {

    Mono<Solution> findById(Long solutionId);

    Mono<Solution> save(Solution solution);

    Mono<Void> delete(Long solutionId);
}
