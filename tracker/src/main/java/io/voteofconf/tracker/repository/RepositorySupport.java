package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Entity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class RepositorySupport {

    private RepositorySupport() {
    }

    /**
     * Exclude case when entity supplied for save have an id but in the same time doesn't exist in storage
     *
     * @param crudRepository
     * @param entity
     * @param expression
     * @param <T>
     * @return
     */
    static <T extends Entity> Mono<T> emptyOrSave(ReactiveCrudRepository<T, Long> crudRepository, T entity, Function<T, Mono<T>> expression) {

        Mono<T> start = Mono.just(entity);

        if (entity.getId() != null)
            start = crudRepository.findById(entity.getId());

        return start.flatMap(arg -> expression.apply(entity));
    }

    static <T extends Entity> Mono<Void> emptyOrDelete(ReactiveCrudRepository<T, Long> crudRepository, Long id, Function<T, Mono<Void>> expression) {
        return id == null ?
                Mono.empty() : crudRepository.findById(id)
                .flatMap(expression);
    }
}
