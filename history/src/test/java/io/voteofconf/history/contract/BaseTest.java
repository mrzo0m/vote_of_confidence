package io.voteofconf.history.contract;

import io.restassured.RestAssured;
import io.voteofconf.history.dao.Backlog;
import io.voteofconf.history.dao.BacklogRebository;
import io.voteofconf.history.dao.ClaimRepository;
import io.voteofconf.history.dao.WebhookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.actuate.autoconfigure.cassandra.CassandraHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.cassandra.ReactiveSession;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@ActiveProfiles(value = "test")
@SpringBootTest(
        properties = {"server.port=0"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {CassandraReactiveDataAutoConfiguration.class,
        CassandraHealthContributorAutoConfiguration.class,
        CassandraDataAutoConfiguration.class
})
@DirtiesContext
@AutoConfigureWireMock(port = 0)
@Import(BaseTest.CassandraConfig.class)
public abstract class BaseTest {


    @LocalServerPort
    protected int port;
    @MockBean
    protected BacklogRebository backlogRebository;

    @MockBean
    protected WebhookRepository webhookRepository;

    @MockBean
    protected ClaimRepository claimRepository;

    @Before
    public void before() throws Exception {

        RestAssured.baseURI = "http://localhost:" + this.port;

        Backlog backlog = new Backlog();
        backlog.setInviteName("Oleg");
        Mockito
                .when(this.backlogRebository.findAll())
                .thenReturn(Flux.just(backlog));

    }

    @Test
    public void test() {

    }

    @Configuration
    public static class CassandraConfig {


        @MockBean
        protected ReactiveSession reactiveSession;

        @MockBean
        protected CassandraConverter cassandraConverter;

        @Bean
        @Order(1)
        public ReactiveCassandraTemplate reactiveCassandraTemplate() {
            return Mockito.mock(ReactiveCassandraTemplate.class);
        }
    }


}
