package io.voteofconf.history.dao;

import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.CassandraUnitTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ClaimRepositoryTest {

    @Autowired
    ClaimRepository claimRepository;

    @Test
    public void persist() throws Exception {

    }

}