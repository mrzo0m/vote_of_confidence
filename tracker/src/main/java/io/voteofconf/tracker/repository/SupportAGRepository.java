package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.model.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SupportAGRepository extends ReactiveCrudRepository<Query, Long> {

    @org.springframework.data.r2dbc.repository.query.Query("select name, source from queries where name = :name")
    Mono<Query> getQuery(String name);

    @org.springframework.data.r2dbc.repository.query.Query("select name, source from queries")
    Flux<Query> getQueries();
}
