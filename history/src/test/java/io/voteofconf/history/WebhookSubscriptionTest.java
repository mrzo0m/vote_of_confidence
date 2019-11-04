package io.voteofconf.history;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.CassandraUnitTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestExecutionListeners({CassandraUnitTestExecutionListener.class})
@CassandraDataSet(value = "cql/webhooks.cql", keyspace = "voc_test_keyspace")
@EmbeddedCassandra
@WebFluxTest
@EnableReactiveCassandraRepositories
public class WebhookSubscriptionTest extends HistoryApplicationTests {
    @Test
    public void myTest() throws Exception {
        Select cql = QueryBuilder.select().from("claim_by_company");
        session.execute(cql);
    }
}
