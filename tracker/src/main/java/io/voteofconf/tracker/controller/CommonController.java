package io.voteofconf.tracker.controller;

import io.voteofconf.common.model.Company;
import io.voteofconf.common.model.Interview;
import io.voteofconf.common.model.Solution;
import io.voteofconf.common.model.User;
import io.voteofconf.tracker.repository.*;
import io.voteofconf.tracker.repository.api.CompanyMWRepository;
import io.voteofconf.tracker.repository.api.InterviewMWRepository;
import io.voteofconf.tracker.repository.api.SolutionMWRepository;
import io.voteofconf.tracker.repository.api.UserMWRepository;
import io.voteofconf.tracker.repository.generated.UserAGCrudRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

//@RestController
public class CommonController {

    private UserAGCrudRepository userAGRepository;
    private UserMWRepository userMWRepository;
    private CompanyMWRepository companyMWRepository;
    private InterviewMWRepository interviewMWRepository;
    private SolutionMWRepository solutionMWRepository;


    public CommonController(UserAGCrudRepository userAGRepository, UserMWRepositoryImpl userMWRepository, CompanyMWRepositoryImpl companyMWRepository, InterviewMWRepositoryImpl interviewMWRepository, SolutionMWRepositoryImpl solutionMWRepository) {
        this.userAGRepository = userAGRepository;
        this.userMWRepository = userMWRepository;
        this.companyMWRepository = companyMWRepository;
        this.interviewMWRepository = interviewMWRepository;
        this.solutionMWRepository = solutionMWRepository;
    }

//    @RequestMapping(method = RequestMethod.GET, path = "/getUserById")
//    public Mono<User> getUserByEmailAddr(@RequestParam Long userId) {
//        return userAGRepository.findById(userId);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/getUserByEmailAddr")
//    public Flux<User> getUserByEmailAddr(@RequestParam String emailAddr) {
//        return userAGRepository.findByEmailAddr(emailAddr);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/getCandidates")
//    public Flux<User> getCandidates() {
//        return userAGRepository.findAllCandidates();
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/getCandidatesByExpertise")
//    public Flux<User> getCandidatesByExpertise(@RequestParam Set<String> keywords) {
//        return userMWRepository.findAllCandidatesByExpertise(keywords);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/getCandidatesByCompanyName")
//    public Flux<User> getCandidatesByCompanyName(@RequestParam String companyName) {
//        return userAGRepository.findCandidatesByCompanyName(companyName);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/getExpertsByExpertise")
//    public Flux<User> getExpertsByExpertise(@RequestParam Set<String> keywords) {
//        return userMWRepository.findAllExpertsByExpertise(keywords);
//    }
//
//    @RequestMapping(method = RequestMethod.POST, path = "/saveUser")
//    public Mono<User> saveUser(@RequestBody User user) {
//        return userMWRepository.save(user);
//    }
//
//    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteUser")
//    public Mono<Void> deleteUser(@RequestParam Long userId) {
//        return userMWRepository.delete(userId);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/getCompanyByName")
//    public Mono<Company> getCompanyByName(@RequestParam String name) {
//        return companyMWRepository.getCompanyByName(name);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/getInterview")
//    public Mono<Interview> getInterview(@RequestParam Long interviewId) {
//        return interviewMWRepository.findById(interviewId);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/getSolution")
//    public Mono<Solution> getSolution(@RequestParam Long solutionId) {
//        return solutionMWRepository.findById(solutionId);
//    }
}
