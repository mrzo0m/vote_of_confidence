package io.voteofconf.tracker.controller;

import io.voteofconf.common.model.Solution;
import io.voteofconf.common.model.User;
import io.voteofconf.tracker.repository.api.CompanyMWRepository;
import io.voteofconf.tracker.repository.api.SolutionMWRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/solution")
public class SolutionController implements EntityController<Solution> {

    private SolutionMWRepository solutionMWRepository;


    public SolutionController(SolutionMWRepository solutionMWRepository) {
        this.solutionMWRepository = solutionMWRepository;
    }

    @GetMapping("/getSolution/{solutionId}")
    @Override
    public Mono<Solution> get(@PathVariable Long solutionId) {
        return solutionMWRepository.findById(solutionId);
    }

    @PostMapping("/createSolution")
    @Override
    public Mono<Solution> create(@RequestBody Solution solution) {
        return solutionMWRepository.save(solution);
    }

    @PutMapping("/updateSolution/{solutionId}")
    @Override
    public Mono<Solution> update(@PathVariable Long solutionId, @RequestBody Solution solution) {
        return solutionMWRepository.save(solution);
    }

    @DeleteMapping("/deleteSolution/{solutionId}")
    @Override
    public Mono<Void> delete(@PathVariable Long solutionId) {
        return solutionMWRepository.delete(solutionId);
    }
}
