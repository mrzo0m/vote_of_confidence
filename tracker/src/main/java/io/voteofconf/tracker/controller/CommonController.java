package io.voteofconf.tracker.controller;

import io.voteofconf.tracker.mapper.UserMapper;
import io.voteofconf.tracker.model.User;
import io.voteofconf.tracker.repository.RowDataProcessor;
import io.voteofconf.tracker.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CommonController {

    private UserDao userDao;

    private UserMapper userMapper;

    @Autowired
    public CommonController(UserDao userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public Flux<User> getUsers() {
        return Mono.fromFuture(userDao.getUsers())
                .flatMapIterable(queryResult -> RowDataProcessor.processData(queryResult, userMapper));
    }
}
