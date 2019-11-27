package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Report;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReportAGRepository extends ReactiveCrudRepository<Report, Long> {
}
