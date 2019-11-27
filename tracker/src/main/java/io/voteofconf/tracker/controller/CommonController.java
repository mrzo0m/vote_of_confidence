package io.voteofconf.tracker.controller;

import io.voteofconf.common.model.Company;
import io.voteofconf.common.model.Interview;
import io.voteofconf.common.model.User;
import io.voteofconf.tracker.repository.CompanyMWRepository;
import io.voteofconf.tracker.repository.InterviewMWRepository;
import io.voteofconf.tracker.repository.UserAGCrudRepository;
import io.voteofconf.tracker.repository.UserMWRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
public class CommonController {

    private UserAGCrudRepository userAGRepository;
    private UserMWRepository userMWRepository;
    private CompanyMWRepository companyMWRepository;
    private InterviewMWRepository interviewMWRepository;


    public CommonController(UserAGCrudRepository userAGRepository, UserMWRepository userMWRepository, CompanyMWRepository companyMWRepository, InterviewMWRepository interviewMWRepository) {
        this.userAGRepository = userAGRepository;
        this.userMWRepository = userMWRepository;
        this.companyMWRepository = companyMWRepository;
        this.interviewMWRepository = interviewMWRepository;
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

    @RequestMapping(method = RequestMethod.POST, path = "/saveUser")
    public Mono<User> createOrUpdateUser(@RequestBody User user) {
        return userMWRepository.save(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteUser")
    public Mono<Void> deleteUser(@RequestBody Long userId) {
        return userMWRepository.delete(userId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getCompanyByName")
    public Mono<Company> getCompanyByName(@RequestParam String name) {
        return companyMWRepository.getCompanyByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getInterview")
    public Mono<Interview> getCompanyByName(@RequestParam Long interviewId) {
        return interviewMWRepository.findById(interviewId);
    }
}
