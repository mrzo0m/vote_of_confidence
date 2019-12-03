package io.voteofconf.tracker.repository;

import io.r2dbc.spi.ConnectionFactory;
import io.voteofconf.common.model.Company;
import io.voteofconf.common.model.Expertise;
import io.voteofconf.common.model.User;
import io.voteofconf.common.model.Vacancy;
import io.voteofconf.tracker.repository.api.UserMWRepository;
import org.springframework.data.r2dbc.core.DatabaseClient;
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
public class UserMWRepositoryImpl implements UserMWRepository {

    private DatabaseClient databaseClient;

    private QueryCachingSupport queryCachingSupport;
    private ExpertiseMWRepositoryImpl expertiseMWRepository;
    private ExpertiseAGRepository expertiseAGRepository;
    private M2MMappingMWRepository.UserExpertiseAGRepository userExpertiseAGRepository;
    private UserAGCrudRepository userAGCrudRepository;
    private M2MMappingMWRepository m2MMappingMWRepository;
    private M2MMappingMWRepository.ClientAgreementsAGRepository clientAgreementsAGRepository;
    private ConnectionFactory connectionFactory;

    public UserMWRepositoryImpl(DatabaseClient databaseClient, QueryCachingSupport queryCachingSupport, ExpertiseMWRepositoryImpl expertiseMWRepository, ExpertiseAGRepository expertiseAGRepository, M2MMappingMWRepository.UserExpertiseAGRepository userExpertiseAGRepository, UserAGCrudRepository userAGCrudRepository, M2MMappingMWRepository m2MMappingMWRepository, M2MMappingMWRepository.ClientAgreementsAGRepository clientAgreementsAGRepository, ConnectionFactory connectionFactory) {
        this.databaseClient = databaseClient;
        this.queryCachingSupport = queryCachingSupport;
        this.expertiseMWRepository = expertiseMWRepository;
        this.expertiseAGRepository = expertiseAGRepository;
        this.userExpertiseAGRepository = userExpertiseAGRepository;
        this.userAGCrudRepository = userAGCrudRepository;
        this.m2MMappingMWRepository = m2MMappingMWRepository;
        this.clientAgreementsAGRepository = clientAgreementsAGRepository;
        this.connectionFactory = connectionFactory;
    }

    @Transactional(readOnly = true, transactionManager = "reactiveTransactionManager")
    public Mono<User> findById(Long userid) {
        String querySource = queryCachingSupport.getQuerySource("selectUserById");

        return databaseClient.execute(querySource)
                .bind("userId", userid)
                .as(User.class)
                .fetch()
                .one()
                .flatMap(user -> expertiseMWRepository
                        .addExpertisesToUsers(Collections.singletonList(user))
                        .then(Mono.just(user)));
    }

    @Override
    public Flux<User> findAllCandidatesByExpertise(Set<String> keywords) {
        return findAllUsersByExpertise(keywords, User.ClientType.CANDIDATE);
    }

    @Override
    public Flux<User> findAllExpertsByExpertise(Set<String> keywords) {
        return findAllUsersByExpertise(keywords, User.ClientType.EXPERT);
    }

    @Override
    @Transactional(readOnly = true, transactionManager = "reactiveTransactionManager")
    public Flux<User> findAllUsersByExpertise(Set<String> keywords, User.ClientType clientType) {
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

    @Override
    @Transactional("reactiveTransactionManager")
    public Mono<User> save(User user) {
        Set<Expertise> expertises = user.getExpertises();
        Boolean agreed = user.getAgreed();

        return RepositorySupport.emptyOrSave(
                userAGCrudRepository,
                user,
                (arg) -> userAGCrudRepository.save(user)
                        .flatMap(usr -> {
                            Set<M2MMappingMWRepository.UserExpertise> set = expertises.stream()
                                    .map(expertise -> new M2MMappingMWRepository.UserExpertise(usr.getId(), expertise.getId()))
                                    .collect(Collectors.toSet());
                            return m2MMappingMWRepository.createOrUpdateM2MRelation(
                                    set,
                                    M2MMappingMWRepository.UserExpertise::getUserId,
                                    M2MMappingMWRepository.UserExpertise::getExpertiseId,
                                    M2MMappingMWRepository.UserExpertise.class
                            ).then(Mono.just(usr));
                        })
                        .flatMap(usr -> createOrUpdateClientAgreements(new M2MMappingMWRepository.ClientAgreements(user.getId(), agreed))
                                .then(Mono.just(usr)))
                        .doOnNext(usr -> usr.setExpertises(expertises))
                        .doOnNext(usr -> usr.setAgreed(agreed)));
    }

//    private Mono<Void> createOrUpdateUserExpertise(Set<M2MMappingMWRepository.UserExpertise> userExpertises) {
//        String querySource = queryCachingSupport.getQuerySource("userExpertiseTupleQuery");
//
//        List<Object[]> tuples = userExpertises.stream()
//                .map(userExpertise -> new Object[]{userExpertise.getUserId(), userExpertise.getExpertiseId()})
//                .collect(Collectors.toList());
//
//        return databaseClient.execute(querySource)
//                .bind("tuples", tuples)
//                .as(M2MMappingMWRepository.UserExpertise.class)
//                .fetch()
//                .all()
//                .collectList()
//                .flatMap(fщundUserExpertises -> {
//                    Set<M2MMappingMWRepository.UserExpertise> set =  new HashSet<>(userExpertises);
//                    set.removeAll(fщundUserExpertises);
//
//                    ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//                    for (M2MMappingMWRepository.UserExpertise ue : set) {
//                        executorService.execute(() -> {
//                            databaseClient.insert()
//                                    .into(M2MMappingMWRepository.UserExpertise.class)
//                                    .using(ue)
//                                    .then()
//                                    .block();
//                        });
//                    }
//                    executorService.shutdown();;
//
//                    return Mono.empty();
//                });
//
//    }

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

    @Override
    public Mono<Void> delete(Long userId) {
        return RepositorySupport.emptyOrDelete(
                userAGCrudRepository,
                userId,
                (user) -> Flux.fromIterable(user.getExpertises())
                        .flatMap(expertise -> m2MMappingMWRepository.deleteM2MRelation(
                                M2MMappingMWRepository.UserExpertise.class,
                                "user_id", user.getId(),
                                "expertise_id", expertise.getId()
                        ))
                        .then(clientAgreementsAGRepository
                                .delete(new M2MMappingMWRepository.ClientAgreements(user.getId(), user.getAgreed())))
                        .then(userAGCrudRepository.delete(user))
        );
    }
}
