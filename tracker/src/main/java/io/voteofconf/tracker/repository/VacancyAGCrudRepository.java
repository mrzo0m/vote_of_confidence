package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Vacancy;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VacancyAGCrudRepository extends ReactiveCrudRepository<Vacancy, Long> {
}
