package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Expertise;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ExpertiseAGRepository extends ReactiveCrudRepository<Expertise, Long> {
}
