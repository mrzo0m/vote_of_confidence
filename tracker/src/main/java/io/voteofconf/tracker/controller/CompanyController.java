package io.voteofconf.tracker.controller;

import io.voteofconf.common.model.Company;
import io.voteofconf.tracker.repository.api.CompanyMWRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/company")
public class CompanyController implements EntityController<Company> {

    private CompanyMWRepository companyMWRepository;

    public CompanyController(CompanyMWRepository companyMWRepository) {
        this.companyMWRepository = companyMWRepository;
    }

    @GetMapping("/getCompany/{companyId}")
    @Override
    public Mono<Company> get(@PathVariable Long companyId) {
        return companyMWRepository.findById(companyId);
    }

    @PostMapping("/createCompany")
    @Override
    public Mono<Company> create(@RequestBody Company company) {
        return companyMWRepository.save(company);
    }

    @PutMapping("/updateCompany/{companyId}")
    @Override
    public Mono<Company> update(@PathVariable Long companyId, @RequestBody Company company) {
        company.setId(companyId);
        return companyMWRepository.save(company);
    }

    @DeleteMapping("/deleteCompany/{companyId}")
    @Override
    public Mono<Void> delete(@PathVariable Long companyId) {
        return companyMWRepository.delete(companyId);
    }

    @GetMapping("/getByName")
    public Mono<Company> getByName(@RequestParam String name) {
        return companyMWRepository.getCompanyByName(name);
    }
}
