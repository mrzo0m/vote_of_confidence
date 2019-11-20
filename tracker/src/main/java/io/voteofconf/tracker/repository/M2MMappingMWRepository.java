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
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
                            .collect(Collectors.groupingBy(UserExpertise::getExpertiseId));
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

    Mono<Collection<Company>> mergeCompanyUser(Map<Long, User> uMap, Map<Long, Company> comMap, List<Long> companyIds) {
        return databaseClient.select()
                .from(CompanyUser.class)
                .matching(where("companyId").in(companyIds))
                .fetch()
                .all()
                .collectList()
                .flatMap(companyUsers -> {
                    Map<Long, List<CompanyUser>> cuMap = companyUsers.stream()
                            .collect(Collectors.groupingBy(CompanyUser::getUserId));
                    cuMap.forEach(  (userId, cuList) -> {
                        User u = uMap.get(userId);
                        cuMap.get(userId)
                                .stream()
                                .filter(cu -> comMap.containsKey(cu.getUserId()))
                                .forEach(cu -> comMap.get(cu.getUserId())
                                        .getUsers()
                                        .add(u));
                    });
                    return Mono.just(comMap.values());
                });
    }

    Mono<Collection<Vacancy>> mergeVacancyUser(Map<Long, User> uMap, Map<Long, Vacancy> vacMap, List<Long> vacancyIds) {
        return databaseClient.select()
                .from(UserVacancy.class)
                .matching(where("vacancyId").in(vacancyIds))
                .fetch()
                .all()
                .collectList()
                .flatMap(userVacancies -> {
                    Map<Long, List<UserVacancy>> vuMap = userVacancies.stream()
                            .collect(Collectors.groupingBy(UserVacancy::getVacancyId));
                    vuMap.forEach(  (userId, cuList) -> {
                        User u = uMap.get(userId);
                        vuMap.get(userId)
                                .stream()
                                .filter(cu -> vacMap.containsKey(cu.getUserId()))
                                .forEach(cu -> vacMap.get(cu.getUserId())
                                        .getUsers()
                                        .add(u));
                    });
                    return Mono.just(vacMap.values());
                });
    }

    <T, R, TR> Mono<Collection<R>> mergeM2MRelation(Map<Long, T> tMap, Map<Long, R> rMap,
                                                    Function<R, Set<T>> collectionExtractor,
                                                    Function<TR, Long> compositeExtractor,
                                                    Function<TR, Long> compositeTIdExtractor,
                                                    Class<TR> compositeClass,
                                                    String compositeIdentifier, List<Long> identifiers) {
        return databaseClient.select()
                .from(compositeClass)
                .matching(where(compositeIdentifier).in(identifiers))
                .fetch()
                .all()
                .collectList()
                .flatMap(composites -> {
                    Map<Long, List<TR>> mtrMap = composites.stream()
                            .collect(Collectors.groupingBy(compositeExtractor));
                    mtrMap.forEach(  (userId, cuList) -> {
                        T t = tMap.get(userId);
                        mtrMap.get(userId)
                                .stream()
                                .filter(tr -> rMap.containsKey(compositeTIdExtractor.apply(tr)))
                                .forEach(tr -> collectionExtractor
                                        .apply(rMap.get(compositeTIdExtractor.apply(tr)))
                                        .add(t));
                    });
                    return Mono.just(rMap.values());
                });
    }
}
