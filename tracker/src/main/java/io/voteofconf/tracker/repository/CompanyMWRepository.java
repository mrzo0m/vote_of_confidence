package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.model.Company;
import io.voteofconf.tracker.repository.support.M2MMappingMWRepository;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import static org.springframework.data.r2dbc.query.Criteria.where;

@Repository
public class CompanyMWRepository {

    private DatabaseClient databaseClient;

    private QueryCachingSupport queryCachingSupport;
    private UserMWRepository userMWRepository;
    private M2MMappingMWRepository m2MMappingMWRepository;
    private VacancyMWRepository vacancyMWRepository;


    public CompanyMWRepository(DatabaseClient databaseClient, QueryCachingSupport queryCachingSupport, UserMWRepository userMWRepository, M2MMappingMWRepository m2MMappingMWRepository, VacancyMWRepository vacancyMWRepository) {
        this.databaseClient = databaseClient;
        this.queryCachingSupport = queryCachingSupport;
        this.userMWRepository = userMWRepository;
        this.m2MMappingMWRepository = m2MMappingMWRepository;
        this.vacancyMWRepository = vacancyMWRepository;
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


}
