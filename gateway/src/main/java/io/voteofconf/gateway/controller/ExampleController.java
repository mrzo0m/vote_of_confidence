package io.voteofconf.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExampleController {

    @RequestMapping("/")
    String home() {
        return "index";
    }

    @RequestMapping("/login")
    String restricted() {
        return "restricted";
    }
}
