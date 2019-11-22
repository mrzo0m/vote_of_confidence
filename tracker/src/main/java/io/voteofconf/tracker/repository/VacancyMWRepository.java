package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.model.Company;
import io.voteofconf.tracker.model.Vacancy;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.springframework.data.r2dbc.query.Criteria.where;

@Repository
public class VacancyMWRepository {

    private DatabaseClient databaseClient;

    private QueryCachingSupport queryCachingSupport;
    private UserMWRepository userMWRepository;
    private M2MMappingMWRepository m2MMappingMWRepository;


    public VacancyMWRepository(DatabaseClient databaseClient, QueryCachingSupport queryCachingSupport, UserMWRepository userMWRepository, M2MMappingMWRepository m2MMappingMWRepository) {
        this.databaseClient = databaseClient;
        this.queryCachingSupport = queryCachingSupport;
        this.userMWRepository = userMWRepository;
        this.m2MMappingMWRepository = m2MMappingMWRepository;
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
}
