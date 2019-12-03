package io.voteofconf.tracker.repository.api;

import io.voteofconf.common.model.User;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface UserMWRepository {
    Flux<User> findAllCandidatesByExpertise(Set<String> keywords);

    Flux<User> findAllExpertsByExpertise(Set<String> keywords);

    @Transactional(readOnly = true, transactionManager = "reactiveTransactionManager")
    Flux<User> findAllUsersByExpertise(Set<String> keywords, User.ClientType clientType);

    @Transactional("reactiveTransactionManager")
    Mono<User> save(User user);

    Mono<Void> delete(Long userId);
}
