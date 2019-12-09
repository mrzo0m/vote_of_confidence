package io.voteofconf.tracker.controller;

import io.voteofconf.common.model.Interview;
import io.voteofconf.tracker.dto.ExpertTimes;
import io.voteofconf.tracker.repository.api.InterviewMWRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/interview")
public class InterviewController implements EntityController<Interview>  {

    private InterviewMWRepository interviewMWRepository;


    public InterviewController(InterviewMWRepository interviewMWRepository) {
        this.interviewMWRepository = interviewMWRepository;
    }

    @GetMapping ("/getInterview/{interviewId}")
    @Override
    public Mono<Interview> get(@PathVariable Long interviewId) {
        return interviewMWRepository.findById(interviewId);
    }

    @PostMapping("/createInterview")
    @Override
    public Mono<Interview> create(@RequestBody Interview interview) {
        return interviewMWRepository.save(interview);
    }

    @PutMapping("/updateInterview/{interviewId}")
    @Override
    public Mono<Interview> update(@PathVariable Long interviewId, @RequestBody Interview interview) {
        interview.setId(interviewId);
        return interviewMWRepository.save(interview);
    }

    @DeleteMapping("/deleteInterview/{interviewId}")
    @Override
    public Mono<Void> delete(@PathVariable Long interviewId) {
        return interviewMWRepository.delete(interviewId);
    }

    @PostMapping("/getAvailableTimes")
    public Flux<LocalDateTime> getAvailableTimes(@RequestBody ExpertTimes expertTimes) {
        return interviewMWRepository.getAvailableTimes(expertTimes);
    }
}
