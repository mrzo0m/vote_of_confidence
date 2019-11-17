package io.voteofconf.tracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = CommonController.class, excludeAutoConfiguration = { ReactiveSecurityAutoConfiguration.class, ReactiveUserDetailsServiceAutoConfiguration.class }
)
@ActiveProfiles("test")
@Slf4j
public class CommonControllerTest {

//    @Autowired
//    private WebTestClient webTestClient;
//
//    @MockBean
//    private UserMapper userMapper;
//
//    @MockBean
//    private UserDao userDao;

//    @Test
    public void getUsersTest() throws ExecutionException, InterruptedException {
//        Map<String, Integer> mapping = new HashMap<>();
//        mapping.put("fisrtName", 1);
//        mapping.put("secondName", 2);
//        mapping.put("surName", 3);
//        mapping.put("emailAddr", 4);
//
//        ArrayRowData data = new ArrayRowData(1, mapping,
//                new Object[]{ "Tom", "Kook", null, "tom.kook@dog.com" });
//        ResultSet resultSet =  Mockito.mock(ResultSet.class);
//
//        doReturn(Stream.of(data))
//                .when(resultSet).stream();
//        doReturn(Arrays.asList("fisrtName", "secondName", "surName", "emailAddr"))
//                .when(resultSet).columnNames();
//        doReturn(false)
//                .when(resultSet).isEmpty();
//
//        CompletableFuture<QueryResult> future = new CompletableFuture<>();
//        QueryResult queryResult = new QueryResult(1, "ok", resultSet);
//
//        future.complete(queryResult);
//        doReturn(future)
//                .when(userDao).getUsers();
//
//        User user = new User(data);
//        doReturn(user)
//                .when(userMapper).map(data);
//
//
//        webTestClient.get()
//                .uri("/users")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBodyList(User.class)
//                .value(users ->users.get(0).getFisrtName(), equalTo("Tom"));
    }
}
