package io.voteofconf.history;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.QueryLogger;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.CassandraUnitTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestExecutionListeners({CassandraUnitTestExecutionListener.class})
@CassandraDataSet(value = "cql/dataset.cql", keyspace = "voc_test_keyspace")
@EmbeddedCassandra
@WebFluxTest
@EnableReactiveCassandraRepositories
public class HistoryApplicationTests {

    public static final boolean QUERY_LOG_ON = true;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9142;
    private static final String KEYSPACE = "voc_test_keyspace"; // this is a fixed value
    private Cluster cluster;
    private Session session;

    @Before
    public void init() {
        // Connect to the cluster (using defaults)
        cluster = Cluster.builder()
                .addContactPoints(HOST)
                .withPort(PORT)
                .build();

        if (QUERY_LOG_ON) {
            // Add a query logger
            QueryLogger queryLogger = QueryLogger.builder()
                    .withConstantThreshold(100)
                    .build();
            cluster.register(queryLogger);
        }

        session = cluster.connect(KEYSPACE);
    }

    @After
    public void tearDown() {
        session.close();
        cluster.close();
    }

    @Test
    public void myTest() throws Exception {

        Select cql = QueryBuilder.select().from("claim_by_company");
        session.execute(cql);
    }

}
