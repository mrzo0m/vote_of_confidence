package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Certificate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CertificateAGRepository extends ReactiveCrudRepository<Certificate, Long> {
}
