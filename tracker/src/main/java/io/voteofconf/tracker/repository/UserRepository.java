package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.model.User;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {


    @Query("SELECT u.first_name, u.second_name, u.sur_name, u.email_addr, t.type ad clientType " +
            "   FROM user u" +
            "   JOIN client_types t " +
            "   ON u.client_type_id = t.id " +
            "WHERE email_addr = :emailAddr")
    Flux<User> findByEmailAddr(String emailAddr);

    @Query("SELECT u.first_name, u.second_name, u.sur_name, u.email_addr, ct.type as client_type, \n" +
            "\t\tact.id  as \"account_type_id\", act.name  as \"account_type_name\", act.cost as \"account_type_cost\", act.description  as \"account_type_description\", act.period  as \"account_type_period\"\n" +
            "\tFROM user u \n" +
            "\tJOIN client_types ct \n" +
            "\t\tON u.client_type_id = ct.id\n" +
            "\tJOIN account_types act \n" +
            "\t\tON u.account_type_id = act.id\t\t\n" +
            "\tWHERE ct.type = 'CANDIDATE'")
    Flux<User> findAllCandidates();
}

