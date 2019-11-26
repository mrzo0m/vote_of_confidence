package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.model.Company;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CompanyAGRepository extends ReactiveCrudRepository<Company, Long> {
}
