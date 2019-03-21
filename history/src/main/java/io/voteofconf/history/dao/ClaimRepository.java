package io.voteofconf.history.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClaimRepository {

    Flux<Claim> findByKeyCompanyName(final String companyName);

    Mono<Claim> findOneByKeyCompanyName(final String companyName);
}
