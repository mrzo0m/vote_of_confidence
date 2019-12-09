package io.voteofconf.tracker.controller;

import io.voteofconf.common.model.User;
import io.voteofconf.tracker.repository.*;
import io.voteofconf.tracker.repository.api.UserMWRepository;
import io.voteofconf.tracker.repository.generated.UserAGCrudRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController implements EntityController<User> {

    private UserAGCrudRepository userAGRepository;
    private UserMWRepository userMWRepository;


    public UserController(UserAGCrudRepository userAGRepository, UserMWRepositoryImpl userMWRepository) {
        this.userAGRepository = userAGRepository;
        this.userMWRepository = userMWRepository;
    }

    @GetMapping("/getUser/{userId}")
    @Override
    public Mono<User> get(@PathVariable Long userId) {
        return userAGRepository.findById(userId);
    }

    @PostMapping("/createUser")
    @Override
    public Mono<User> create(@RequestBody User user) {
        return userMWRepository.save(user);
    }

    @PutMapping("/updateUser/{userId}")
    @Override
    public Mono<User> update(@PathVariable Long userId, @RequestBody User user) {
        return userMWRepository.save(user);
    }

    @DeleteMapping("/deleteUser/{userId}")
    @Override
    public Mono<Void> delete(@PathVariable Long userId) {
        return userMWRepository.delete(userId);
    }

    @GetMapping("/getByEmailAddr")
    public Flux<User> getUserByEmailAddr(@RequestParam String emailAddr) {
        return userAGRepository.findByEmailAddr(emailAddr);
    }

    @GetMapping("/getCandidates")
    public Flux<User> getCandidates() {
        return userAGRepository.findAllCandidates();
    }

    @GetMapping("/getCandidatesByExpertise")
    public Flux<User> getCandidatesByExpertise(@RequestParam Set<String> keywords) {
        return userMWRepository.findAllCandidatesByExpertise(keywords);
    }

    @GetMapping( "/getCandidatesByCompanyName")
    public Flux<User> getCandidatesByCompanyName(@RequestParam String companyName) {
        return userAGRepository.findCandidatesByCompanyName(companyName);
    }

    @GetMapping("/getExpertsByExpertise")
    public Flux<User> getExpertsByExpertise(@RequestParam Set<String> keywords) {
        return userMWRepository.findAllExpertsByExpertise(keywords);
    }
}
