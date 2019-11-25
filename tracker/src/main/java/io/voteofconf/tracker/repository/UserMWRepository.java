package io.voteofconf.tracker.repository;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import io.r2dbc.spi.Batch;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Statement;
import io.voteofconf.tracker.model.*;
import io.voteofconf.tracker.repository.support.ClientAgreementsAGRepository;
import io.voteofconf.tracker.repository.support.M2MMappingMWRepository;
import io.voteofconf.tracker.repository.support.UserExpertiseAGRepository;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.springframework.data.r2dbc.query.Criteria.where;

@Repository
public class UserMWRepository {

    private DatabaseClient databaseClient;

    private QueryCachingSupport queryCachingSupport;
    private ExpertiseMWRepository expertiseMWRepository;
    private ExpertiseAGRepository expertiseAGRepository;
    private UserExpertiseAGRepository userExpertiseAGRepository;
    private UserAGCrudRepository userAGCrudRepository;
    private M2MMappingMWRepository m2MMappingMWRepository;
    private ClientAgreementsAGRepository clientAgreementsAGRepository;
    private ConnectionFactory connectionFactory;

    public UserMWRepository(DatabaseClient databaseClient, QueryCachingSupport queryCachingSupport, ExpertiseMWRepository expertiseMWRepository, ExpertiseAGRepository expertiseAGRepository, UserExpertiseAGRepository userExpertiseAGRepository, UserAGCrudRepository userAGCrudRepository, M2MMappingMWRepository m2MMappingMWRepository, ClientAgreementsAGRepository clientAgreementsAGRepository) {
        this.databaseClient = databaseClient;
        this.queryCachingSupport = queryCachingSupport;
        this.expertiseMWRepository = expertiseMWRepository;
        this.expertiseAGRepository = expertiseAGRepository;
        this.userExpertiseAGRepository = userExpertiseAGRepository;
        this.userAGCrudRepository = userAGCrudRepository;
        this.m2MMappingMWRepository = m2MMappingMWRepository;
        this.clientAgreementsAGRepository = clientAgreementsAGRepository;
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
    public Mono<User> saveUser(User user) {
//        TransactionalOperator operator = TransactionalOperator.create(reactiveTransactionManager);

        Set<Expertise> expertises = user.getExpertises();
        Boolean agreed = user.getAgreed();
        Set<M2MMappingMWRepository.UserExpertise> userExpertises = expertises.stream()
                .map(expertise -> new M2MMappingMWRepository.UserExpertise(user.getId(), expertise.getId()))
                .collect(Collectors.toSet());

        return userAGCrudRepository.save(user)
                .flatMap(usr -> createOrUpdateUserExpertise(userExpertises)
                        .then(Mono.just(usr)))
                .flatMap(usr -> createOrUpdateClientAgreements(new M2MMappingMWRepository.ClientAgreements(user.getId(), agreed))
                        .then(Mono.just(usr)))
                .doOnNext(usr -> usr.setExpertises(expertises))
                .doOnNext(usr -> usr.setAgreed(agreed));

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

    private Mono<Void> createOrUpdateUserExpertise(Set<M2MMappingMWRepository.UserExpertise> userExpertises) {
        List<Object[]> tuples = userExpertises.stream()
                .map(userExpertise -> new Object[]{userExpertise.getUserId(), userExpertise.getExpertiseId()})
                .collect(Collectors.toList());

        return databaseClient.execute("select * from user_expertise where (user_id, expertise_id) in (:tuples)")
                .bind("tuples", tuples)
                .as(M2MMappingMWRepository.UserExpertise.class)
                .fetch()
                .all()
                .collectList()
                .flatMap(fщundUserExpertises -> {
                    Set<M2MMappingMWRepository.UserExpertise> set =  new HashSet<>(userExpertises);
                    set.removeAll(fщundUserExpertises);

                    ExecutorService executorService = Executors.newSingleThreadExecutor();

                    for (M2MMappingMWRepository.UserExpertise ue : set) {
                        executorService.execute(() -> {
                            databaseClient.insert()
                                    .into(M2MMappingMWRepository.UserExpertise.class)
                                    .using(ue)
                                    .then()
                                    .block();
                        });
                    }
                    executorService.shutdown();;

                    return Mono.empty();
                });

    }

    private Mono<M2MMappingMWRepository.ClientAgreements> createOrUpdateClientAgreements(M2MMappingMWRepository.ClientAgreements clientAgreements) {
        return databaseClient.select()
                .from(M2MMappingMWRepository.ClientAgreements.class)
                .matching(where("user_id").is(clientAgreements.getUserId()))
                .fetch()
                .one()
                .doOnNext(clientAgreementsAGRepository::save)
                .switchIfEmpty(databaseClient.insert()
                        .into(M2MMappingMWRepository.ClientAgreements.class)
                        .using(clientAgreements)
                        .fetch()
                        .rowsUpdated()
                        .then(Mono.just(clientAgreements)));

    }

    public Mono<Void> deleteUser(User user) {
        return Flux.fromIterable(user.getExpertises())
                .flatMap(expertise -> userExpertiseAGRepository.delete(
                        new M2MMappingMWRepository.UserExpertise(user.getId(), expertise.getId())))
                .then(clientAgreementsAGRepository
                        .delete(new M2MMappingMWRepository.ClientAgreements(user.getId(), user.getAgreed())))
        .then(userAGCrudRepository.delete(user));

//        return databaseClient.delete()
//                .from(User.class)
//                .matching(where("id").is(user.getId()))
//                .fetch()
//                .rowsUpdated()
//                .single();
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
