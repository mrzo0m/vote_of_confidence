package io.voteofconf.history.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableReactiveCassandraRepositories
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {


    private CassandraKubeConfig cassandraKubeConfig;

    @Autowired
    public CassandraConfig(CassandraKubeConfig cassandraKubeConfig) {
        this.cassandraKubeConfig = cassandraKubeConfig;
    }

    @Override
    @Scheduled(fixedDelay = 500000)
    protected String getKeyspaceName() {
        System.out.println("The keyspace is: " + cassandraKubeConfig.getKeyspace());
        return cassandraKubeConfig.getKeyspace();
    }

    @Override
    @Scheduled(fixedDelay = 500000)
    protected String getContactPoints() {
        System.out.println("The cassandra host is: " + cassandraKubeConfig.getContactPoints());
        return cassandraKubeConfig.getContactPoints();
    }

    @Override
    @Scheduled(fixedDelay = 500000)
    protected int getPort() {
        System.out.println("The cassandra port is: " + cassandraKubeConfig.getPort());
        return cassandraKubeConfig.getPort();
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{
                cassandraKubeConfig.getBasePackages()
        };
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(cassandraKubeConfig.getKeyspace())
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return Collections.singletonList(specification);
    }


}