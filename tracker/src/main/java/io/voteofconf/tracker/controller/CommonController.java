package io.voteofconf.tracker.controller;

import io.voteofconf.tracker.model.User;
import io.voteofconf.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class CommonController {

    private UserRepository userRepository;

    @Autowired
    public CommonController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getCandidates")
    public Flux<User> getUsers() {
        return userRepository.findAllCandidates();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user")
    public Flux<User> getUser(@RequestParam String emailAddr) {
        return userRepository.findByEmailAddr(emailAddr);
    }
}
