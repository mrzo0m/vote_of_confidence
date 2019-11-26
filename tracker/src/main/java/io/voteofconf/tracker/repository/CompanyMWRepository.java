package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Company;
import io.voteofconf.common.model.User;
import io.voteofconf.common.model.Vacancy;
import io.voteofconf.tracker.repository.support.M2MMappingMWRepository;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.r2dbc.query.Criteria.where;

@Repository
public class CompanyMWRepository {

    private DatabaseClient databaseClient;

    private QueryCachingSupport queryCachingSupport;
    private UserMWRepository userMWRepository;
    private M2MMappingMWRepository m2MMappingMWRepository;
    private VacancyMWRepository vacancyMWRepository;
    private CompanyAGRepository companyAGRepository;


    public CompanyMWRepository(DatabaseClient databaseClient, QueryCachingSupport queryCachingSupport, UserMWRepository userMWRepository, M2MMappingMWRepository m2MMappingMWRepository, VacancyMWRepository vacancyMWRepository, CompanyAGRepository companyAGRepository) {
        this.databaseClient = databaseClient;
        this.queryCachingSupport = queryCachingSupport;
        this.userMWRepository = userMWRepository;
        this.m2MMappingMWRepository = m2MMappingMWRepository;
        this.vacancyMWRepository = vacancyMWRepository;
        this.companyAGRepository = companyAGRepository;
    }

    @Transactional(readOnly = true, transactionManager = "reactiveTransactionManager")
    public Mono<Company> getCompanyByName(String name) {
        return databaseClient.select()
                .from(Company.class)
                .matching(where("name").is(name))
                .fetch()
                .one()
                .flatMap(company -> vacancyMWRepository.getVacanciesByCompany(company))
                .flatMap(company -> userMWRepository.addUsersToCompany(company));

    }

    @Transactional("reactiveTransactionManager")
    public Mono<Company> save(Company company) {
        Set<User> users = company.getUsers();
        Set<Vacancy> vacancies = company.getVacancies();

        return RepositorySupport.emptyOrSave(
                companyAGRepository,
                company,
                (arg) -> companyAGRepository.save(company)
                        .flatMap(cmp -> {
                            Set<M2MMappingMWRepository.CompanyUser> set = users.stream()
                                    .map(user -> new M2MMappingMWRepository.CompanyUser(user.getId(), company.getId()))
                                    .collect(Collectors.toSet());
                            return m2MMappingMWRepository.createOrUpdateM2MRelation(
                                    set,
                                    M2MMappingMWRepository.CompanyUser::getUserId,
                                    M2MMappingMWRepository.CompanyUser::getCompanyId,
                                    M2MMappingMWRepository.CompanyUser.class
                            ).then(Mono.just(company));
                        })
                        .flatMap(cmp -> {
                            Set<M2MMappingMWRepository.CompanyVacancy> set = vacancies.stream()
                                    .map(vacancy -> new M2MMappingMWRepository.CompanyVacancy(company.getId(), vacancy.getId()))
                                    .collect(Collectors.toSet());
                            return m2MMappingMWRepository.createOrUpdateM2MRelation(
                                    set,
                                    M2MMappingMWRepository.CompanyVacancy::getCompanyId,
                                    M2MMappingMWRepository.CompanyVacancy::getVacancyId,
                                    M2MMappingMWRepository.CompanyVacancy.class
                            ).then(Mono.just(company));
                        })
                        .doOnNext(usr -> usr.setUsers(users))
                        .doOnNext(usr -> usr.setVacancies(vacancies)));
    }


    public Mono<Void> delete(Long userId) {
        return RepositorySupport.emptyOrDelete(
                companyAGRepository,
                userId,
                (company) -> Flux.fromIterable(company.getUsers())
                        .flatMap(user -> m2MMappingMWRepository.deleteM2MRelation(
                                M2MMappingMWRepository.CompanyUser.class,
                                "user_id", user.getId(),
                                "company_id", company.getId()
                        ))
                        .then(Flux.fromIterable(company.getVacancies())
                                .flatMap(vacancy -> m2MMappingMWRepository.deleteM2MRelation(
                                        M2MMappingMWRepository.CompanyVacancy.class,
                                        "company_id", company.getId(),
                                        "vacancy_id", vacancy.getId()
                                ))
                                .then(companyAGRepository.delete(company))));
    }

}
