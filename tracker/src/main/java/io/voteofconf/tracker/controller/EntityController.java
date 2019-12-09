package io.voteofconf.tracker.controller;

import reactor.core.publisher.Mono;

/**
 * REST interface
 *
 * @param <T>
 */
public interface EntityController<T> {

    Mono<T> get(Long id);

    Mono<T> create(T t);

    Mono<T> update(Long id, T t);

    Mono<Void> delete(Long id);
}
