package io.voteofconf.tracker.controller;

import io.voteofconf.common.model.Interview;
import io.voteofconf.tracker.dto.ExpertTimes;
import io.voteofconf.tracker.repository.InterviewMWRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController("/interview")
public class InterviewController {

    private InterviewMWRepository interviewMWRepository;


    public InterviewController(InterviewMWRepository interviewMWRepository) {
        this.interviewMWRepository = interviewMWRepository;
    }

    @PostMapping("/registerInterview")
    public Mono<Interview> registerInterview(@RequestBody Interview interview) {
        return interviewMWRepository.save(interview);
    }

    @PostMapping("/getAvailableTimes")
    public Flux<LocalDateTime> getAvailableTimes(@RequestBody ExpertTimes expertTimes) {
        return interviewMWRepository.getAvailableTimes(expertTimes);
    }

    @PutMapping("/updateInterview/{interviewId}")
    public Mono<Interview> registerInterview(@PathVariable Long interviewId, @RequestBody Interview interview) {
        interview.setId(interviewId);
        return interviewMWRepository.save(interview);
    }

    @DeleteMapping("/deleteInterview/{interviewId}")
    public Mono<Void> deleteInterview(@PathVariable Long interviewId) {
        return interviewMWRepository.delete(interviewId);
    }
}
