package io.voteofconf.history.controller;

import io.swagger.annotations.ApiOperation;
import io.voteofconf.history.dao.Claim;
import io.voteofconf.history.dao.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("claim")
public class ClaimController {

    private ClaimRepository claimRepository;

    @Autowired
    public ClaimController(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @GetMapping("/company/{name}")
    public Mono<Claim> getClaimByCompany(@PathVariable(value = "name") String companyName) {
        return claimRepository.findOneByKeyCompanyName(companyName);
    }


    @GetMapping("/{id}")
    public Mono<Claim> get(@PathVariable(value = "id") UUID id) {
        return claimRepository.findOneByKeyId(id);
    }


    @ApiOperation(value = "Post to save claim")
    @PostMapping
    public Mono<ResponseEntity<Claim>> save(@RequestBody Claim claim) {
        return this.claimRepository.save(claim)
                .map(savedClaim -> new ResponseEntity<>(savedClaim, HttpStatus.CREATED));
    }


}
