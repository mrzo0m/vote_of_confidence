package io.voteofconf.history.dao;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClaimRepository extends ReactiveCassandraRepository<Claim, ClaimKey> {

    Flux<Claim> findByKeyCompanyName(final String companyName);

    Mono<Claim> findOneByKeyCompanyName(final String companyName);
}