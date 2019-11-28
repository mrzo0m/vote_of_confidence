package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Company;
import io.voteofconf.common.model.User;
import io.voteofconf.common.model.Vacancy;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.r2dbc.query.Criteria.where;

@Repository
public class VacancyMWRepository {

    private DatabaseClient databaseClient;

    private QueryCachingSupport queryCachingSupport;
    private UserMWRepository userMWRepository;
    private M2MMappingMWRepository m2MMappingMWRepository;
    private VacancyAGCrudRepository vacancyAGCrudRepository;


    public VacancyMWRepository(DatabaseClient databaseClient, QueryCachingSupport queryCachingSupport, UserMWRepository userMWRepository, M2MMappingMWRepository m2MMappingMWRepository, VacancyAGCrudRepository vacancyAGCrudRepository) {
        this.databaseClient = databaseClient;
        this.queryCachingSupport = queryCachingSupport;
        this.userMWRepository = userMWRepository;
        this.m2MMappingMWRepository = m2MMappingMWRepository;
        this.vacancyAGCrudRepository = vacancyAGCrudRepository;
    }

    public Mono<Company> getVacanciesByCompany(Company company) {
        return m2MMappingMWRepository.mergeM2MRelation(
                (vcvMap, vacancyIds) -> databaseClient.select()
                        .from(Vacancy.class)
                        .matching(where("id").in(vacancyIds))
                        .fetch()
                        .all()
                        .collectList()
                        .flatMap(vacancies -> userMWRepository.addUsersToVacancies(vacancies))
                        .doOnNext(vacancies -> company.getVacancies().addAll(vacancies))
                        .then(Mono.just(company))
                        .flux(),
//                        .doOnNext(vacancy -> company.getVacancies().add(vacancy)),
                M2MMappingMWRepository.CompanyVacancy::getVacancyId,
                M2MMappingMWRepository.CompanyVacancy.class,
                "companyId",
                Collections.singletonList(company.getId())).single();
    }

    public Mono<Vacancy> save(Vacancy vacancy) {
        Set<User> users = vacancy.getUsers();

        return RepositorySupport.emptyOrSave(
                vacancyAGCrudRepository,
                vacancy,
                (arg) -> vacancyAGCrudRepository.save(vacancy)
                        .flatMap(cmp -> {
                            Set<M2MMappingMWRepository.UserVacancy> set = users.stream()
                                    .map(user -> new M2MMappingMWRepository.UserVacancy(user.getId(), vacancy.getId()))
                                    .collect(Collectors.toSet());
                            return m2MMappingMWRepository.createOrUpdateM2MRelation(
                                    set,
                                    M2MMappingMWRepository.UserVacancy::getUserId,
                                    M2MMappingMWRepository.UserVacancy::getVacancyId,
                                    M2MMappingMWRepository.UserVacancy.class
                            ).then(Mono.just(vacancy));
                        })
                        .doOnNext(vc -> vc.setUsers(users)));
    }
    public Mono<Void> delete(Long vacancyId) {
        return RepositorySupport.emptyOrDelete(
                vacancyAGCrudRepository,
                vacancyId,
                (vacancy) -> Flux.fromIterable(vacancy.getUsers())
                        .flatMap(user -> m2MMappingMWRepository.deleteM2MRelation(
                                M2MMappingMWRepository.CompanyVacancy.class,
                                "user_id", user.getId(),
                                "vacancy_id", vacancy.getId()
                        ))
                        .then(vacancyAGCrudRepository.delete(vacancy)));
    }

}
