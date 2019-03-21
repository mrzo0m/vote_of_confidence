package io.voteofconf.history;

import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@TestExecutionListeners({CassandraUnitDependencyInjectionTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@CassandraDataSet(value = "cql/dataset.cql", keyspace = "voc_test_keyspace")
@EmbeddedCassandra
@WebFluxTest
@EnableAutoConfiguration
@EnableCassandraRepositories
@EnableReactiveCassandraRepositories
public class HistoryApplicationTests {

    @Ignore
    @Test
    public void contextLoads() {
    }

}
