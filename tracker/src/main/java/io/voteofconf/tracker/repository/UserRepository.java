package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.model.Expertise;
import io.voteofconf.tracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.r2dbc.query.Criteria.where;

public abstract class UserRepository implements ReactiveCrudRepository<User, Long> {

    @Autowired
    private DatabaseClient databaseClient;

    @Query("SELECT u.first_name, u.second_name, u.sur_name, u.email_addr, t.type ad clientType " +
            "   FROM user u" +
            "   JOIN client_types t " +
            "   ON u.client_type_id = t.id " +
            "WHERE email_addr = :emailAddr")
    abstract Flux<User> findByEmailAddr(String emailAddr);

    @Query("SELECT u.first_name, u.second_name, u.sur_name, u.email_addr, ct.type as client_type, \n" +
            "\t\tact.id  as \"accountType_id\", act.name  as \"accountType_name\", act.cost as \"accountType_cost\", act.description  as \"accountType_description\", act.period  as \"accountType_period\"\n" +
            "\tFROM user u \n" +
            "\tJOIN client_types ct \n" +
            "\t\tON u.client_type_id = ct.id\n" +
            "\tJOIN account_types act \n" +
            "\t\tON u.account_type_id = act.id\t\t\n" +
            "\tWHERE ct.type = 'CANDIDATE'")
    abstract Flux<User> findAllCandidates();

    Flux<User> findAllCandidatesByExpertise(List<String> keywords) {
        DatabaseClient.TypedSelectSpec<Expertise> tss = databaseClient.select().from(Expertise.class);
        Criteria.CriteriaStep criteriaStep = where("keywords");
        Criteria criteria = null;
        for (String keyword : keywords) {
            criteria = criteriaStep.like(keyword);
            criteriaStep = criteria.or("keywords");
        }

        Flux<Expertise> expertises = criteria == null ?
                tss.fetch().all() : tss.matching(criteria).fetch().all();

        databaseClient.execute("")
    }


    // получить всех экспертов по данной области (знаний)
    // добавить/редактировать кандидата
    // удалить кандидата
    // получить кандидата по данной области
    // добавить/редактировать эксперта
    // удалить эксперта
    // подтвердить соглашение
    // добавить/редактировать компанию
    // удалить компанию
    // добавить/редактировать вакансию
    // удалить вакансию
    // присвоить эжксперту область знаний
    // удалить у эксперта область знаний
    // зарегистрировать заявку на интервью
    // удалить заявку на интервью
    // зарегистрировать решение
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
}

