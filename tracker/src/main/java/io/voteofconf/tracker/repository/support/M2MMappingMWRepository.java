package io.voteofconf.tracker.repository.support;

import io.voteofconf.common.model.Expertise;
import io.voteofconf.common.model.User;
import io.voteofconf.tracker.repository.QueryCachingSupport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.data.r2dbc.query.Criteria.where;

@Repository
public class M2MMappingMWRepository {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table("user_expertise")
    public static class UserExpertise {
        private Long userId;
        private Long expertiseId;
    };

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table("company_users")
    public static class CompanyUser {
        private Long userId;
        private Long companyId;
    };

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table("user_vacancies")
    public static class UserVacancy {
        private Long userId;
        private Long vacancyId;
    };

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table("company_vacancies")
    public static class CompanyVacancy {
        private Long companyId;
        private Long vacancyId;
    };

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table("client_agreements")
    public static class ClientAgreements {
        @Id
        private Long userId;
        private Boolean agreed;
    }

    private DatabaseClient databaseClient;

    private QueryCachingSupport queryCachingSupport;


    public M2MMappingMWRepository(DatabaseClient databaseClient, QueryCachingSupport queryCachingSupport) {
        this.databaseClient = databaseClient;
        this.queryCachingSupport = queryCachingSupport;
    }

    Mono<Collection<User>> mergeUserExpertise(Map<Long, User> uMap, Map<Long, Expertise> expMap, List<Long> expertiseIds) {
        return databaseClient.select()
                .from(UserExpertise.class)
                .matching(where("expertiseId").in(expertiseIds))
                .fetch()
                .all()
                .collectList()
                .flatMap(userExpertises -> {
                    Map<Long, List<UserExpertise>> ueMap = userExpertises.stream()
                            .collect(Collectors.groupingBy(UserExpertise::getUserId));
                    Set<Long> userIds = userExpertises.stream()
                            .map(UserExpertise::getUserId)
                            .collect(Collectors.toSet());

                    ueMap.forEach((expId, ueList) -> {
                        Expertise e = expMap.get(expId);
                        ueMap.get(expId)
                                .stream()
                                .filter(ue -> uMap.containsKey(ue.getUserId()))
                                .forEach(ue -> uMap.get(ue.getUserId())
                                        .getExpertises()
                                        .add(e));
                    });
                    return Mono.just(uMap.values());
                });
    }

    public <R, TR> Flux<R> mergeM2MRelation(
            BiFunction<Map<Long, List<TR>>, Set<Long>, Flux<R>> extractR,
            Function<TR, Long> compositeExtractor,
            Class<TR> compositeClass,
            String compositeIdentifier, List<Long> identifiers) {
        return identifiers.isEmpty() ?
                Flux.empty() : databaseClient.select()
                .from(compositeClass)
                .matching(where(compositeIdentifier).in(identifiers))
                .fetch()
                .all()
                .collectList()
                .flatMapMany(composites -> {
                    Map<Long, List<TR>> mtrMap = composites.stream()
                            .collect(Collectors.groupingBy(compositeExtractor));
                    Set<Long> userIds = composites.stream()
                            .map(compositeExtractor)
                            .collect(Collectors.toSet());
                    return extractR.apply(mtrMap, userIds);
                });
    }

    public <TR> Mono<Void> createOrUpdateM2MRelation(Set<TR> compositeEntities,
                                                     Function<TR, Long> leftCompositeExtractor,
                                                     Function<TR, Long> rightCompositeExtractor,
                                                     Class<TR> compositeClass) {
        if (compositeEntities.isEmpty()) return Mono.empty();

        String querySource = queryCachingSupport.getQuerySource("userExpertiseTupleQuery");

        List<Object[]> tuples = compositeEntities.stream()
                .map(ce -> new Object[]{leftCompositeExtractor.apply(ce), rightCompositeExtractor.apply(ce)})
                .collect(Collectors.toList());

        return databaseClient.execute(querySource)
                .bind("tuples", tuples)
                .as(compositeClass)
                .fetch()
                .all()
                .collectList()
                .flatMap(foundUserExpertises -> {
                    Set<TR> set =  new HashSet<>(compositeEntities);
                    set.removeAll(foundUserExpertises);

                    ExecutorService executorService = Executors.newSingleThreadExecutor();

                    for (TR ce : set) {
                        executorService.execute(() -> {
                            databaseClient.insert()
                                    .into(compositeClass)
                                    .using(ce)
                                    .then()
                                    .block();
                        });
                    }
                    executorService.shutdown();;

                    return Mono.empty();
                });
    }

    public <TR> Mono<Void> deleteM2MRelation(Class<TR> compositeClass,
                                             String leftCompositeName, Long leftCompositeId,
                                             String rightCompositeName, Long rightCompositeId) {
        return databaseClient
                .delete()
                .from(compositeClass)
                .matching(where(leftCompositeName).is(leftCompositeId)
                        .and(rightCompositeName).is(rightCompositeId))
                .then();
    }
}
