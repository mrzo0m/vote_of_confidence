package io.voteofconf.tracker;

import io.voteofconf.tracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class Initializer {

    private final UserRepository or;

    Initializer(UserRepository or) {
        this.or = or;
    }

//    @EventListener(ApplicationReadyEvent.class)
    public void go() {
        this.or.findAll().subscribe(user -> log.info(user.toString()));
    }
}