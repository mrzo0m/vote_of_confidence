package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.model.Expertise;
import io.voteofconf.tracker.model.User;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.r2dbc.query.Criteria.where;

@Repository
public class UserMWCrudRepository {

    private DatabaseClient databaseClient;

    private ExpertiseMWRepository expertiseMWRepository;
    private M2MMappingMWRepository m2MMappingMWRepository;

    public UserMWCrudRepository(DatabaseClient databaseClient, ExpertiseMWRepository expertiseMWRepository, M2MMappingMWRepository m2MMappingMWRepository) {
        this.databaseClient = databaseClient;
        this.expertiseMWRepository = expertiseMWRepository;
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
        StringBuilder query = new StringBuilder("select u.id, u.first_name, u.second_name, u.sur_name, u.email_addr, ct.type as client_type, \n" +
                "\t\tact.id  as \"accountType_id\", act.name  as \"accountType_name\", act.cost as \"accountType_cost\", act.description  as \"accountType_description\", act.period  as \"accountType_period\"\n" +
                "\tfrom user u\n" +
                "\tJOIN client_types ct \n" +
                "\t\tON u.client_type_id = ct.id\n" +
                "\tJOIN account_types act \n" +
                "\t\tON u.account_type_id = act.id\t\n" +
                "\tjoin user_expertise ue\n" +
                "\t\ton ue.user_id = u.id \n" +
                "\twhere ue.expertise_id in (:expertiseIds)" +
                "\t\tand ct.type = :clientType\n" +
                "\tgroup by 1,2,3,4,5,6,7,8,9,10");


        return expertiseMWRepository.getExpertisesByKeywords(keywords)
                .collectList()
                .flatMap(expertise -> {
                    List<Long> expertiseIds = expertise.stream()
                            .map(Expertise::getId)
                            .collect(Collectors.toList());
                    Map<Long, Expertise> expMap = expertise.stream()
                            .collect(Collectors.toMap(Expertise::getId, e -> e));
                    return databaseClient.execute(query.toString())
                            .bind("expertiseIds", expertiseIds)
                            .bind("clientType", clientType.name())
                            .as(User.class)
                            .fetch()
                            .all()
                            .collectList()
                            .flatMap(users -> {
                                Map<Long, User> uMap = users.stream()
                                        .collect(Collectors.toMap(User::getId, e -> e));
                                return m2MMappingMWRepository.mergeM2MRelation(
                                        expMap,
                                        uMap,
                                        User::getExpertises,
                                        M2MMappingMWRepository.UserExpertise::getExpertiseId,
                                        M2MMappingMWRepository.UserExpertise::getUserId,
                                        M2MMappingMWRepository.UserExpertise.class,
                                        "expertiseId",
                                        expertiseIds);
//                                return expertiseMWRepository.mergeUserExpertise(uMap, expMap, expertiseIds);
                            });
                })
                .flatMapMany(Flux::fromIterable);
    }



    @Transactional("reactiveTransactionManager")
    public Mono<Integer> createOrUpdateUser(User user) {
//        TransactionalOperator operator = TransactionalOperator.create(reactiveTransactionManager);

        if (user.getId() != null) {
            return databaseClient.select()
                    .from(User.class)
                    .matching(where("id").is(user.getId()))
                    .fetch()
                    .all()
                    .count()
                    .flatMap(count -> {
                        if (count == 0) {
                            return databaseClient.insert()
                                    .into(User.class)
                                    .using(user)
                                    .fetch()
                                    .rowsUpdated()
                                    .concatWith(addUserExpertise(user))
                                    .reduce(Integer::sum)
                                    .single();
                        } else {
                            return databaseClient.update()
                                    .table(User.class)
                                    .using(user)
                                    .fetch()
                                    .rowsUpdated()
                                    .concatWith(addUserExpertise(user))
                                    .reduce(Integer::sum)
                                    .single();
                        }
                    });
        } else {
            return databaseClient.insert()
                    .into(User.class)
                    .using(user)
                    .fetch()
                    .rowsUpdated()
                    .concatWith(addUserExpertise(user))
                    .reduce(Integer::sum)
                    .single();
        }
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
