package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.model.Company;
import io.voteofconf.tracker.model.Expertise;
import io.voteofconf.tracker.model.User;
import io.voteofconf.tracker.model.Vacancy;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.r2dbc.query.Criteria.where;

@Repository
public class UserMWRepository {

    private DatabaseClient databaseClient;

    private QueryCachingSupport queryCachingSupport;
    private ExpertiseMWRepository expertiseMWRepository;
    private UserAGCrudRepository userAGCrudRepository;
    private M2MMappingMWRepository m2MMappingMWRepository;

    public UserMWRepository(DatabaseClient databaseClient, QueryCachingSupport queryCachingSupport, ExpertiseMWRepository expertiseMWRepository, UserAGCrudRepository userAGCrudRepository, M2MMappingMWRepository m2MMappingMWRepository) {
        this.databaseClient = databaseClient;
        this.queryCachingSupport = queryCachingSupport;
        this.expertiseMWRepository = expertiseMWRepository;
        this.userAGCrudRepository = userAGCrudRepository;
        this.m2MMappingMWRepository = m2MMappingMWRepository;
    }

    public Flux<User> findAllCandidatesByExpertise(Set<String> keywords) {
        return findAllUsersByExpertise(keywords, User.ClientType.CANDIDATE);
    }

    public Flux<User> findAllExpertsByExpertise(Set<String> keywords) {
        return findAllUsersByExpertise(keywords, User.ClientType.EXPERT);
    }

    @Transactional(readOnly = true, transactionManager = "reactiveTransactionManager")
    private Flux<User> findAllUsersByExpertise(Set<String> keywords, User.ClientType clientType) {
        String querySource = queryCachingSupport.getQuerySource("selectUsersWithExpertisesByClientType");

        return expertiseMWRepository.getExpertisesByKeywords(keywords)
                .collectList()
                .flatMapMany(expertise -> {
                    List<Long> expertiseIds = expertise.stream()
                            .map(Expertise::getId)
                            .collect(Collectors.toList());
                    Map<Long, Expertise> expMap = expertise.stream()
                            .collect(Collectors.toMap(Expertise::getId, e -> e));

                    return  m2MMappingMWRepository.mergeM2MRelation(
                            (uueMap, userIds) -> databaseClient.execute(querySource)
                                    .bind("userIds", userIds)
                                    .bind("clientType", clientType.name())
                                    .as(User.class)
                                    .fetch()
                                    .all()
                                    .doOnNext(user -> {
                                        uueMap.get(user.getId()).stream()
                                                .filter(ue -> expMap.get(ue.getExpertiseId()) != null)
                                                .forEach(userExpertise -> user.getExpertises()
                                                        .add(expMap.get(userExpertise.getExpertiseId())));
                                    }),
                            M2MMappingMWRepository.UserExpertise::getUserId,
                            M2MMappingMWRepository.UserExpertise.class,
                            "expertiseId",
                            expertiseIds);
                });
    }

    Mono<List<Vacancy>> addUsersToVacancies(List<Vacancy> vacancies) {
        String querySource = queryCachingSupport.getQuerySource("selectUsersWithExpertises");
        Map<Long, Vacancy> vacMap = vacancies.stream()
                .collect(Collectors.toMap(Vacancy::getId, e -> e));

        return m2MMappingMWRepository.mergeM2MRelation(
                (uuvMap, userIds) -> databaseClient.execute(querySource)
                        .bind("userIds", userIds)
                        .as(User.class)
                        .fetch()
                        .all()
                        .doOnNext(user -> uuvMap.get(user.getId()).stream()
                                .filter(uv -> vacMap.get(uv.getVacancyId()) != null)
                                .forEach(uv -> vacMap.get(uv.getVacancyId())
                                        .getUsers()
                                        .add(user)))
                        .collectList()
                        .flatMap(users -> expertiseMWRepository.addExpertisesToUsers(users))
                        .then(Mono.just(vacancies))
                        .flatMapMany(Flux::fromIterable),
                M2MMappingMWRepository.UserVacancy::getUserId,
                M2MMappingMWRepository.UserVacancy.class,
                "vacancyId",
                vacancies.stream()
                        .map(Vacancy::getId)
                        .collect(Collectors.toList())).collectList();
    }


    Mono<Company> addUsersToCompany(Company company) {
        String querySource = queryCachingSupport.getQuerySource("selectUsersWithExpertises");

        return m2MMappingMWRepository.mergeM2MRelation(
                (ucuMap, userIds) -> databaseClient.execute(querySource)
                        .bind("userIds", userIds)
                        .as(User.class)
                        .fetch()
                        .all()
                        .collectList()
                        .flatMap(users -> expertiseMWRepository.addExpertisesToUsers(users))
                        .doOnNext(users -> company.getUsers().addAll(users))
                        .then(Mono.just(company))
                        .flux(),
                M2MMappingMWRepository.CompanyUser::getUserId,
                M2MMappingMWRepository.CompanyUser.class,
                "companyId",
                Collections.singletonList(company.getId())).single();
    }

    @Transactional("reactiveTransactionManager")
    public Mono<User> createOrUpdateUser(User user) {
//        TransactionalOperator operator = TransactionalOperator.create(reactiveTransactionManager);

        return userAGCrudRepository.save(user);
//        if (user.getId() != null) {
//            return databaseClient.execute("select count(&) from user")
//                    .bind("id", user.getId())
//                    .as(Integer.class)
//                    .fetch()
//                    .one()
//                    .flatMap(count -> {
//                        if (count == 0) {
//                            return databaseClient.insert()
//                                    .into(User.class)
//                                    .using(user)
//                                    .fetch()
//                                    .rowsUpdated()
//                                    .concatWith(addUserExpertise(user))
//                                    .reduce(Integer::sum)
//                                    .single();
//                        } else {
//                            return databaseClient.update()
//                                    .table(User.class)
//                                    .using(user)
//                                    .fetch()
//                                    .rowsUpdated()
//                                    .concatWith(addUserExpertise(user))
//                                    .reduce(Integer::sum)
//                                    .single();
//                        }
//                    });
//        } else {
//            return databaseClient.insert()
//                    .into(User.class)
//                    .using(user)
//                    .fetch()
//                    .rowsUpdated()
//                    .concatWith(addUserExpertise(user))
//                    .reduce(Integer::sum)
//                    .single();
//        }
    }

    public Mono<Integer> deleteUser(User user) {
        return databaseClient.delete()
                .from(User.class)
                .matching(where("id").is(user.getId()))
                .fetch()
                .rowsUpdated()
                .single();
    }



    @Transactional("reactiveTransactionManager")
    private Mono<Integer> addUserExpertise(User user) {

        if (user.getExpertises().isEmpty()) {
            return Mono.just(0);
        }

        Criteria.CriteriaStep criteriaStep = where("id");
        Criteria criteria = null;

        for (Expertise expertise : user.getExpertises()) {
            criteria = criteriaStep.is(expertise.getId());
            criteriaStep = criteria.or("id");
        }

        return databaseClient.select()
                .from(Expertise.class)
                .matching(criteria)
                .fetch()
                .all()
                .flatMap(expertise -> databaseClient
                        .insert()
                        .into("user_expertise")
                        .value("expertise_id", expertise.getId())
                        .value("user_id", user.getId())
                        .fetch()
                        .rowsUpdated()
                        .switchIfEmpty(Mono.just(0)))
                .reduce(Integer::sum)
                .single();
    }
}
