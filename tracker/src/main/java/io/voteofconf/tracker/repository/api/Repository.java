package io.voteofconf.tracker.repository.api;

import reactor.core.publisher.Mono;

public interface Repository<T> {

    Mono<T> findById(Long id);

    Mono<T> save(T t);

    Mono<Void> delete(Long id);
}
