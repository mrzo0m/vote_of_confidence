package io.voteofconf.history.controller;

import io.swagger.annotations.ApiOperation;
import io.voteofconf.history.dao.Claim;
import io.voteofconf.history.dao.ClaimKey;
import io.voteofconf.history.dao.ClaimRepository;
import lombok.extern.java.Log;
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
@Log
@RestController
@RequestMapping("claims")
public class ClaimController {

    private ClaimRepository claimRepository;

    @Autowired
    public ClaimController(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Claim> getAll() {
        log.info("Getting all claims");
        return claimRepository.findAll();
    }



    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Claim> get(@PathVariable(value = "id") UUID id) {
        log.info("Getting claim " + id.toString());
        return claimRepository.findOneByKeyId(id);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> put() {
        log.info("Generate stub for claims");
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
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Claim>> save(@RequestBody Claim claim) {
        log.info("Saving some claim");
        return this.claimRepository.save(claim)
                .map(savedClaim -> new ResponseEntity<>(savedClaim, HttpStatus.CREATED));
    }


}
