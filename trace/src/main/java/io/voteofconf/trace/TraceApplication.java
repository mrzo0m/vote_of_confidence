package io.voteofconf.trace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
public class TraceApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(TraceApplication.class, args);
    }


}
