package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.model.Expertise;
import io.voteofconf.tracker.model.User;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Set;

@Repository
public class ExpertiseMWRepository {

    private DatabaseClient databaseClient;
    private M2MMappingMWRepository expertiseMWRepository;

    public ExpertiseMWRepository(DatabaseClient databaseClient, M2MMappingMWRepository expertiseMWRepository) {
        this.databaseClient = databaseClient;
        this.expertiseMWRepository = expertiseMWRepository;
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
}
