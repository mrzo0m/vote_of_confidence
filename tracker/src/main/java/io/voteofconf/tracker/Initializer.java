package io.voteofconf.tracker;

import io.voteofconf.tracker.repository.generated.UserAGCrudRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class Initializer {

    private final UserAGCrudRepository or;

    Initializer(UserAGCrudRepository or) {
        this.or = or;
    }

//    @EventListener(ApplicationReadyEvent.class)
    public void go() {
        this.or.findAll().subscribe(user -> log.info(user.toString()));
    }
}