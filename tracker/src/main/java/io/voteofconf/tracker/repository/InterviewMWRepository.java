package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Interview;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class InterviewMWRepository {

    private DatabaseClient databaseClient;
    private InterviewAGRepository interviewAGRepository;
    private M2MMappingMWRepository m2MMappingMWRepository;
    private UserMWRepository userMWRepository;
    private ExpertiseAGRepository expertiseAGRepository;


    public InterviewMWRepository(DatabaseClient databaseClient, InterviewAGRepository interviewAGRepository, M2MMappingMWRepository m2MMappingMWRepository, UserMWRepository userMWRepository, ExpertiseAGRepository expertiseAGRepository) {
        this.databaseClient = databaseClient;
        this.interviewAGRepository = interviewAGRepository;
        this.m2MMappingMWRepository = m2MMappingMWRepository;
        this.userMWRepository = userMWRepository;
        this.expertiseAGRepository = expertiseAGRepository;
    }

    public Mono<Interview> findById(Long interviewId) {
        return interviewAGRepository
                .findById(interviewId)
                .flatMap(interview -> userMWRepository
                        .findById(interview.getCandidateId())
                        .flatMap(user -> {
                            interview.setCandidate(user);
                            return Mono.just(interview);
                        }))
                .flatMap(interview -> userMWRepository
                        .findById(interview.getExpertId())
                        .flatMap(user -> {
                            interview.setExpert(user);
                            return Mono.just(interview);
                        }))
                .flatMap(interview -> expertiseAGRepository
                        .findById(interview.getDisciplineId())
                        .flatMap(expertise -> {
                            interview.setDiscipline(expertise);
                            return Mono.just(interview);
                        }));
    }

    public Mono<Interview> save(Interview interview) {
        return RepositorySupport.emptyOrSave(
                interviewAGRepository,
                interview,
                interviewAGRepository::save);
    }

    public Mono<Void> delete(Long interviewId) {
        return RepositorySupport.emptyOrDelete(
                interviewAGRepository,
                interviewId,
                interviewAGRepository::delete);
    }
}
