package io.voteofconf.history.dao;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebhookRepository extends ReactiveCassandraRepository<Claim, ClaimKey> {
}
