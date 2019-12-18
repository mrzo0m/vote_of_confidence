package io.voteofconf.history;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.voteofconf.history.controller.BacklogController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@RunWith(SpringRunner.class)
@WebFluxTest(BacklogController.class)
public class BaseAccurest {


    @Rule
    public WireMockRule rule = new WireMockRule(
            wireMockConfig().port(8000)
    );

    @MockBean
    BacklogController backlogController;

    @Before
    public void setup() {

    }
}
