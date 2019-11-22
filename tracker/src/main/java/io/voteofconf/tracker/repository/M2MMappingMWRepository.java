package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.model.Company;
import io.voteofconf.tracker.model.Expertise;
import io.voteofconf.tracker.model.User;
import io.voteofconf.tracker.model.Vacancy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        private long userId;
        private long expertiseId;
    };

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table("company_users")
    public static class CompanyUser {
        private long userId;
        private long companyId;
    };

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table("user_vacancies")
    public static class UserVacancy {
        private long userId;
        private long vacancyId;
    };

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table("company_vacancies")
    public static class CompanyVacancy {
        private long companyId;
        private long vacancyId;
    };


    private DatabaseClient databaseClient;

    public M2MMappingMWRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
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

    <T, R, TR> Flux<R> mergeM2MRelation(
            BiFunction<Map<Long, List<TR>>, Set<Long>, Flux<R>> extractR,
            Function<TR, Long> compositeExtractor,
            Class<TR> compositeClass,
            String compositeIdentifier, List<Long> identifiers) {
        return databaseClient.select()
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
}
