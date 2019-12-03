package io.voteofconf.tracker.repository.api;

import io.voteofconf.common.model.Expertise;
import io.voteofconf.common.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public interface ExpertiseMWRepository {
    Flux<Expertise> getExpertisesByKeywords(Set<String> keywords);

    Flux<Expertise> getExpertiseByUser(User user);

    Mono<Expertise> save(Expertise expertise);

    Mono<Void> delete(Long vacancyId);
}
