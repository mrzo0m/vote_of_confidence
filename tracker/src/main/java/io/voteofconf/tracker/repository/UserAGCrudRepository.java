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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.r2dbc.query.Criteria.where;

public interface UserAGCrudRepository extends ReactiveCrudRepository<User, Long> {

//    User save(User entity);
//
//    Optional<User> findById(Long id);
//
//    Iterable<User> findAll();
//
//    long count();
//
//    void delete(User entity);
//
//    boolean existsById(Long id);

    @Query("SELECT u.id, u.first_name, u.second_name, u.sur_name, u.email_addr, ct.type as client_type, \n" +
            "\t\tact.id  as \"accountType_id\", act.name  as \"accountType_name\", act.cost as \"accountType_cost\", act.description  as \"accountType_description\", act.period  as \"accountType_period\"\n" +
            "\tFROM user u \n" +
            "\tJOIN client_types ct \n" +
            "\t\tON u.client_type_id = ct.id\n" +
            "\tJOIN account_types act \n" +
            "\t\tON u.account_type_id = act.id\t\t\n" +
            "WHERE email_addr = :emailAddr")
    Flux<User> findByEmailAddr(String emailAddr);

    @Query("SELECT u.id, u.first_name, u.second_name, u.sur_name, u.email_addr, ct.type as client_type, \n" +
            "\t\tact.id  as \"accountType_id\", act.name  as \"accountType_name\", act.cost as \"accountType_cost\", act.description  as \"accountType_description\", act.period  as \"accountType_period\"\n" +
            "\tFROM user u \n" +
            "\tJOIN client_types ct \n" +
            "\t\tON u.client_type_id = ct.id\n" +
            "\tJOIN account_types act \n" +
            "\t\tON u.account_type_id = act.id\t\t\n" +
            "\tWHERE ct.type = 'CANDIDATE'")
    Flux<User> findAllCandidates();

    @Query("select u.first_name, u.second_name, u.sur_name, u.email_addr, ct.type as client_type, \n" +
            "\t\tact.id  as \"account_type.id\", act.name  as \"account_type.name\", act.cost as \"account_type.cost\", act.description  as \"account_type.description\", act.period  as \"account_type.period\"\n" +
            "\tfrom user u\n" +
            "\tJOIN client_types ct \n" +
            "\t\tON u.client_type_id = ct.id\n" +
            "\tJOIN account_types act \n" +
            "\t\tON u.account_type_id = act.id\n" +
            "\tjoin vacancy v\n" +
            "\t\ton v.user_id = u.id\n" +
            "\tjoin company c\n" +
            "\t\ton c.id = v.company_id\n" +
            "\twhere ct.type = 'CANDIDATE' and c.name = :companyName")
    Flux<User> findCandidatesByCompanyName(String companyName);




    // получить всех кандидатов по данной области (знаний)
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

