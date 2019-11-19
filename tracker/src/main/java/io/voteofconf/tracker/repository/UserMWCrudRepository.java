package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.exception.R2dbcException;
import io.voteofconf.tracker.model.Expertise;
import io.voteofconf.tracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.r2dbc.query.Criteria.where;

@Repository
public class UserMWCrudRepository {

    private DatabaseClient databaseClient;
    private ReactiveTransactionManager reactiveTransactionManager;

    public UserMWCrudRepository(DatabaseClient databaseClient, ReactiveTransactionManager reactiveTransactionManager) {
        this.databaseClient = databaseClient;
        this.reactiveTransactionManager = reactiveTransactionManager;
    }

    public Flux<User> findAllCandidatesByExpertise(Set<String> keywords) {
        return findAllUsersByExpertise(keywords, User.ClientType.CANDIDATE);
    }

    public Flux<User> findAllExpertsByExpertise(Set<String> keywords) {
        return findAllUsersByExpertise(keywords, User.ClientType.EXPERT);
    }

    private Flux<User> findAllUsersByExpertise(Set<String> keywords, User.ClientType clientType) {
        StringBuilder query = new StringBuilder("select u.id, u.first_name, u.second_name, u.sur_name, u.email_addr, ct.type as client_type, \n" +
                "\t\tact.id  as \"accountType_id\", act.name  as \"accountType_name\", act.cost as \"accountType_cost\", act.description  as \"accountType_description\", act.period  as \"accountType_period\"\n" +
                "\tfrom user u\n" +
                "\tJOIN client_types ct \n" +
                "\t\tON u.client_type_id = ct.id\n" +
                "\tJOIN account_types act \n" +
                "\t\tON u.account_type_id = act.id\t\t\n" +
                "\tjoin user_expertise ue\n" +
                "\t\ton u.id = ue.user_id\n" +
                "\tjoin expertise e\n" +
                "\t\ton ue.expertise_id = e.id\n" +
                "\t\twhere ct.type = '" + clientType.name() + "'\n");

        DatabaseClient.TypedSelectSpec<Expertise> tss = databaseClient.select().from(Expertise.class);
        String clause = " and (e.keywords like";
        for (String keyword : keywords) {
            query.append(String.format(" %s '%%%s%%' ", clause, keyword));
            clause = "or e.keywords like";
        }

        if (keywords.size() > 0) query.append(")");

        return databaseClient.execute(query.toString())
                .as(User.class)
                .fetch()
                .all()
                .flatMap(user -> getExpertiseByUser(user)
                        .collect(HashSet<Expertise>::new, Set::add)
                        .doOnNext(user::setExpertises)
                        .then(Mono.just(user)));

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

    private Flux<Expertise> getExpertiseByUser(User user) {
        return databaseClient.execute("select e.* from expertise e " +
                "join user_expertise ue " +
                "   on e.id = ue.expertise_id " +
                "join user u " +
                "   on ue.user_id = u.id " +
                "where u.id = :userId")
                .bind("userId", user.getId())
                .as(Expertise.class)
                .fetch()
                .all();
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
