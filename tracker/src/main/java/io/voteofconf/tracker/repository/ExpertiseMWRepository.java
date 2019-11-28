package io.voteofconf.tracker.repository;

import io.voteofconf.common.model.Expertise;
import io.voteofconf.common.model.User;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.r2dbc.query.Criteria.where;

@Repository
public class ExpertiseMWRepository {

    private DatabaseClient databaseClient;
    private ExpertiseAGRepository expertiseAGRepository;
    private M2MMappingMWRepository m2MMappingMWRepository;


    public ExpertiseMWRepository(DatabaseClient databaseClient, ExpertiseAGRepository expertiseAGRepository, M2MMappingMWRepository m2MMappingMWRepository) {
        this.databaseClient = databaseClient;
        this.expertiseAGRepository = expertiseAGRepository;
        this.m2MMappingMWRepository = m2MMappingMWRepository;
    }


    Flux<Expertise> getExpertisesByKeywords(Set<String> keywords) {
        StringBuilder query = new StringBuilder("select *\n" +
                "\t\tfrom expertise  e\n");

        DatabaseClient.TypedSelectSpec<Expertise> tss = databaseClient.select().from(Expertise.class);
        String clause = " where e.keywords like";
        for (String keyword : keywords) {
            query.append(String.format(" %s '%%%s%%' ", clause, keyword));
            clause = "or e.keywords like";
        }

        return databaseClient.execute(query.toString())
                .as(Expertise.class)
                .fetch()
                .all();
    }


    private Flux<Expertise> getExpertiseByUser(User user) {
        return databaseClient.execute("select e.* from expertise e " +
                "join user_expertise ue " +
                "   on e.id = ue.expertise_id " +
                "join user u " +
                "   on ue.user_id = u.id " +
                "where u.id = :userId")
                .bind("userId", user.getId())
                .as(Expertise.class)
                .fetch()
                .all();
    }

    Mono<List<User>> addExpertisesToUsers(List<User> users) {
        Map<Long, User> uMap = users.stream()
                .collect(Collectors.toMap(User::getId, e -> e));

        return m2MMappingMWRepository.mergeM2MRelation(
                (eueMap, expertiseIds) -> databaseClient.select()
                        .from(Expertise.class)
                        .matching(where("id").in(expertiseIds))
                        .fetch()
                        .all()
                        .doOnNext(expertise -> eueMap.get(expertise.getId()).stream()
                                .filter(ue -> uMap.get(ue.getUserId()) != null)
                                .forEach(ue -> uMap.get(ue.getUserId())
                                .getExpertises()
                                .add(expertise)))
                        .collectList()
                        .then(Mono.just(users))
                        .flatMapMany(Flux::fromIterable),
                M2MMappingMWRepository.UserExpertise::getExpertiseId,
                M2MMappingMWRepository.UserExpertise.class,
                "userId",
                users.stream()
                        .map(User::getId)
                        .collect(Collectors.toList())).collectList();
    }

    public Mono<Expertise> save(Expertise expertise) {
        return RepositorySupport.emptyOrSave(
                expertiseAGRepository,
                expertise,
                expertiseAGRepository::save);
    }
    public Mono<Void> delete(Long vacancyId) {
        return RepositorySupport.emptyOrDelete(
                expertiseAGRepository,
                vacancyId,
                expertiseAGRepository::delete);
    }
}
