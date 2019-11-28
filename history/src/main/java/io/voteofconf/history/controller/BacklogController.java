package io.voteofconf.history.controller;

import io.swagger.annotations.ApiOperation;
import io.voteofconf.history.dao.Backlog;
import io.voteofconf.history.dao.BacklogRebository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("backlog")
public class BacklogController {

    @Autowired
    BacklogRebository backlogRebository;

    @ApiOperation(value = "All task at backlog")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Backlog> getBacklog() {
        return backlogRebository.findAll();
    }


}
