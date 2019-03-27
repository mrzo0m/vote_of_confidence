package io.voteofconf.history.controller;

import io.swagger.annotations.ApiOperation;
import io.voteofconf.history.dao.Claim;
import io.voteofconf.history.dao.ClaimKey;
import io.voteofconf.history.dao.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

/*
 *  Best Practice for api:
 *   /farmers
 *   /farmers/{farmer_id}
 *   /farmers/{farmer_id}/arrivals
 *   /farmers/{farmer_id}/arrivals/{id}
 *   /crops
 *   /crops/{crop_id}
 */
@RestController
@RequestMapping("claims")
public class ClaimController {

    private ClaimRepository claimRepository;

    @Autowired
    public ClaimController(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }


    @GetMapping
    public Flux<Claim> getAll() {
        return claimRepository.findAll();
    }

    @GetMapping("/{company}")
    public Mono<Claim> getClaimByCompany(@PathVariable(value = "company") String companyName) {
        return claimRepository.findOneByKeyCompanyName(companyName);
    }


    @GetMapping("/{id}")
    public Mono<Claim> get(@PathVariable(value = "id") UUID id) {
        return claimRepository.findOneByKeyId(id);
    }


    @PutMapping
    public Mono<Void> put() {
        claimRepository.saveAll(Arrays.asList(
                new Claim(new ClaimKey("Company"+UUID.randomUUID(), UUID.randomUUID()), "info: " + System.nanoTime()),
                new Claim(new ClaimKey("Company"+UUID.randomUUID(), UUID.randomUUID()), "info: " + System.nanoTime()),
                new Claim(new ClaimKey("Company"+UUID.randomUUID(), UUID.randomUUID()), "info: " + System.nanoTime()),
                new Claim(new ClaimKey("Company"+UUID.randomUUID(), UUID.randomUUID()), "info: " + System.nanoTime()),
                new Claim(new ClaimKey("Company"+UUID.randomUUID(), UUID.randomUUID()), "info: " + System.nanoTime())
        )).subscribe();
        return Mono.empty();
    }


    @ApiOperation(value = "Post to save claim")
    @PostMapping
    public Mono<ResponseEntity<Claim>> save(@RequestBody Claim claim) {
        return this.claimRepository.save(claim)
                .map(savedClaim -> new ResponseEntity<>(savedClaim, HttpStatus.CREATED));
    }


}
