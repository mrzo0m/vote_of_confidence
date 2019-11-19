package io.voteofconf.tracker.controller;

import io.voteofconf.tracker.model.Expertise;
import io.voteofconf.tracker.model.User;
import io.voteofconf.tracker.repository.UserAGCrudRepository;
import io.voteofconf.tracker.repository.UserMWCrudRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@RestController
public class CommonController {

    private UserAGCrudRepository userAGRepository;
    private UserMWCrudRepository userMWRepository;

    public CommonController(UserAGCrudRepository userAGRepository, UserMWCrudRepository userMWRepository) {
        this.userAGRepository = userAGRepository;
        this.userMWRepository = userMWRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getUserByEmailAddr")
    public Flux<User> getUserByEmailAddr(@RequestParam String emailAddr) {
        return userAGRepository.findByEmailAddr(emailAddr);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getCandidates")
    public Flux<User> getCandidates() {
        return userAGRepository.findAllCandidates();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getCandidatesByExpertise")
    public Flux<User> getCandidatesByExpertise(@RequestParam Set<String> keywords) {
        return userMWRepository.findAllCandidatesByExpertise(keywords);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getCandidatesByCompanyName")
    public Flux<User> getCandidatesByCompanyName(@RequestParam String companyName) {
        return userAGRepository.findCandidatesByCompanyName(companyName);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getExpertsByExpertise")
    public Flux<User> getExpertsByExpertise(@RequestParam Set<String> keywords) {
        return userMWRepository.findAllExpertsByExpertise(keywords);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/createOrUpdateUser")
    public Mono<Integer> createOrUpdateUser(@RequestBody User user) {
        return userMWRepository.createOrUpdateUser(user);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/deleteUser")
    public Mono<Integer> deleteUser(@RequestBody User user) {
        return userMWRepository.deleteUser(user);
    }

}
