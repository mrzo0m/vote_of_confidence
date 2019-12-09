package io.voteofconf.tracker.controller;

import io.voteofconf.common.model.Vacancy;
import io.voteofconf.tracker.repository.VacancyMWRepositoryImpl;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vacancy")
public class VacancyController implements EntityController<Vacancy> {

    private VacancyMWRepositoryImpl vacancyMWRepository;


    public VacancyController(VacancyMWRepositoryImpl vacancyMWRepository) {
        this.vacancyMWRepository = vacancyMWRepository;
    }

    @GetMapping("/getVacancy/{vacancyId}")
    @Override
    public Mono<Vacancy> get(@PathVariable Long vacancyId) {
        return vacancyMWRepository.findById(vacancyId);
    }

    @PostMapping("/postVacancy")
    @Override
    public Mono<Vacancy> create(@RequestBody Vacancy vacancy) {
        return vacancyMWRepository.save(vacancy);
    }

    @PutMapping("/putVacancy/{vacancyId}")
    @Override
    public Mono<Vacancy> update(@PathVariable Long vacancyId, @RequestBody Vacancy vacancy) {
        return vacancyMWRepository.save(vacancy);
    }

    @DeleteMapping("/deleteVacancy/{vacancyId}")
    @Override
    public Mono<Void> delete(@PathVariable Long vacancyId) {
        return vacancyMWRepository.delete(vacancyId);
    }
}
