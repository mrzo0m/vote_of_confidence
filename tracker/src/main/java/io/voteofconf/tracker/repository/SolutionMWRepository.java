package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Interview;
import io.voteofconf.common.model.Solution;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class SolutionMWRepository {

    private DatabaseClient databaseClient;

    private InterviewMWRepository interviewMWRepository;
    private SolutionAGRepository solutionAGRepository;
    private CertificateAGRepository certificateAGRepository;
    private ReportAGRepository reportAGRepository;


    public SolutionMWRepository(DatabaseClient databaseClient, InterviewMWRepository interviewMWRepository, SolutionAGRepository solutionAGRepository, CertificateAGRepository certificateAGRepository, ReportAGRepository reportAGRepository) {
        this.databaseClient = databaseClient;
        this.interviewMWRepository = interviewMWRepository;
        this.solutionAGRepository = solutionAGRepository;
        this.certificateAGRepository = certificateAGRepository;
        this.reportAGRepository = reportAGRepository;
    }

    public Mono<Solution> findById(Long solutionId) {
        return solutionAGRepository
                .findById(solutionId)
                .flatMap(solution -> interviewMWRepository
                        .findById(solution.getInterviewApplicationId())
                        .flatMap(interview -> {
                            solution.setInterview(interview);
                            return Mono.just(solution);
                        }))
                .flatMap(solution -> certificateAGRepository
                        .findById(solution.getCertificateId())
                        .flatMap(certificate -> {
                            solution.setCertificate(certificate);
                            return Mono.just(solution);
                        }))
                .flatMap(solution -> reportAGRepository
                        .findById(solution.getReportId())
                        .flatMap(report -> {
                            solution.setReport(report);
                            return Mono.just(solution);
                        }));
    }

    public Mono<Solution> save(Solution solution) {
        return RepositorySupport.emptyOrSave(
                solutionAGRepository,
                solution,
                solutionAGRepository::save);
    }

    public Mono<Void> delete(Long solutionId) {
        return RepositorySupport.emptyOrDelete(
                solutionAGRepository,
                solutionId,
                solutionAGRepository::delete);
    }
}
