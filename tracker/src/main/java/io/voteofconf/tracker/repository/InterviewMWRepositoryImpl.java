package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Interview;
import io.voteofconf.tracker.dto.ExpertTimes;
import io.voteofconf.tracker.repository.api.InterviewMWRepository;
import io.voteofconf.tracker.repository.generated.ExpertiseAGRepository;
import io.voteofconf.tracker.repository.generated.InterviewAGRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
@Slf4j
public class InterviewMWRepositoryImpl implements InterviewMWRepository {

    private DatabaseClient databaseClient;
    private InterviewAGRepository interviewAGRepository;
    private M2MMappingMWRepository m2MMappingMWRepository;
    private UserMWRepositoryImpl userMWRepository;
    private ExpertiseAGRepository expertiseAGRepository;
    private QueryCachingSupport queryCachingSupport;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


    public InterviewMWRepositoryImpl(DatabaseClient databaseClient, InterviewAGRepository interviewAGRepository, M2MMappingMWRepository m2MMappingMWRepository, UserMWRepositoryImpl userMWRepository, ExpertiseAGRepository expertiseAGRepository, QueryCachingSupport queryCachingSupport) {
        this.databaseClient = databaseClient;
        this.interviewAGRepository = interviewAGRepository;
        this.m2MMappingMWRepository = m2MMappingMWRepository;
        this.userMWRepository = userMWRepository;
        this.expertiseAGRepository = expertiseAGRepository;
        this.queryCachingSupport = queryCachingSupport;
    }


    @Override
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

    @Override
    public Mono<Interview> save(Interview interview) {
        return RepositorySupport.emptyOrSave(
                interviewAGRepository,
                interview,
                interviewAGRepository::save);
    }

    @Override
    public Mono<Void> delete(Long interviewId) {
        return RepositorySupport.emptyOrDelete(
                interviewAGRepository,
                interviewId,
                interviewAGRepository::delete);
    }

    @Override
    public Flux<LocalDateTime> getAvailableTimes(ExpertTimes expertTimes) {
        final String selectUnionPattern = " select '%s' as date_time ";

        String unionExpr = expertTimes.getTimes().stream()
                .map(localDateTime -> String.format(selectUnionPattern, localDateTime.toString()))
                .reduce((s, s2) -> s.concat(" union "))
                .orElse("");

        if (unionExpr.isEmpty()) return Flux.empty();

        String querySource = queryCachingSupport.getQuerySource("selectAvailableTimesForInterview");

        String query = String.format(querySource, unionExpr);

        return databaseClient.execute(query)
                .bind("userId", expertTimes.getExpertId())
                .map((row, rowMetadata) -> LocalDateTime.parse((CharSequence) row.get("date_time"), formatter))
                .all();
//                .doOnNext(localDateTime -> log.info(localDateTime.toString()));
    }
}
