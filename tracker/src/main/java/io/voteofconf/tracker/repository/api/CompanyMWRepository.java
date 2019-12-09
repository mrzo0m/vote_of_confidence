package io.voteofconf.tracker.repository.api;

import io.voteofconf.common.model.Company;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface CompanyMWRepository extends Repository<Company> {

    @Transactional(readOnly = true, transactionManager = "reactiveTransactionManager")
    Mono<Company> findById(Long id);

    @Transactional(readOnly = true, transactionManager = "reactiveTransactionManager")
    Mono<Company> getCompanyByName(String name);

    @Transactional("reactiveTransactionManager")
    Mono<Company> save(Company company);

    Mono<Void> delete(Long userId);
}
