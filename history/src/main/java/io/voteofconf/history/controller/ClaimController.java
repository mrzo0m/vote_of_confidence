package io.voteofconf.history.controller;

import io.voteofconf.history.dao.Claim;
import io.voteofconf.history.dao.ClaimKey;
import io.voteofconf.history.dao.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("claim")
public class ClaimController {

    private static final String TO_THE_STARS = "ToTheStars";

    @Autowired
    private ClaimRepository claimRepository;

    @GetMapping("demo")
    public Mono<Claim> getHi() {
        final Claim toTheStarsClaim =
                new Claim(new ClaimKey(TO_THE_STARS, UUID.randomUUID()), "This is info");
        claimRepository.insert(toTheStarsClaim).subscribe();

        return claimRepository.findOneByKeyCompanyName(TO_THE_STARS);
    }

}
