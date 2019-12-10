package io.voteofconf.tracker.contract;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.internal.mapping.Jackson1Mapper;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.config.RestAssuredMockMvcConfig;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import io.restassured.module.webtestclient.config.RestAssuredWebTestClientConfig;
import io.restassured.module.webtestclient.config.WebTestClientConfig;
import io.restassured.path.json.mapper.factory.DefaultJackson1ObjectMapperFactory;
import io.restassured.path.json.mapper.factory.DefaultJackson2ObjectMapperFactory;
import io.restassured.path.json.mapper.factory.Jackson1ObjectMapperFactory;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import io.voteofconf.common.model.*;
import io.voteofconf.tracker.controller.*;
import io.voteofconf.tracker.dto.ExpertTimes;
import io.voteofconf.tracker.repository.api.*;
import io.voteofconf.tracker.repository.generated.UserAGCrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.wst.xml.xpath2.api.Item;
import org.junit.Before;
import org.omg.CORBA.OBJ_ADAPTER;
import org.reactivestreams.Publisher;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.config;

@Slf4j
public class InterviewRestBase {
    private Company company;
    private Interview interview;
    private Solution solution;
    private Vacancy vacancy;
    private User user;

    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    {
        try {
            company = OBJECT_MAPPER.readValue(getStream(Company.class), Company.class);
            interview = OBJECT_MAPPER.readValue(getStream(Interview.class), Interview.class);
            solution = OBJECT_MAPPER.readValue(getStream(Solution.class), Solution.class);
            vacancy = OBJECT_MAPPER.readValue(getStream(Vacancy.class), Vacancy.class);
            user = OBJECT_MAPPER.readValue(getStream(User.class), User.class);
        } catch (IOException e) {
            log.error("Cannot initialize tests :", e);
        }
    }

    private InputStream getStream(Class clazz) {
        String name = clazz.getSimpleName(), uncapitalizedName = name.toLowerCase();
        return this.getClass().getClassLoader().getResourceAsStream(
                String.format("contracts/%s/create%s/rest/output.json", uncapitalizedName, name));
    }

    @Before
    public void setup() {
        RestAssuredWebTestClient
                .standaloneSetup(
                        createStubCompanyController(),
                        createStubInterviewController(),
                        createStubSolutionController(),
                        createStubUserController(),
                        createStubVacancyController()
                );
        // remove::end[]
    }

    private CompanyController createStubCompanyController() {
        return new CompanyController(
                new CompanyMWRepository() {
                    @Override
                    public Mono<Company> save(Company company) {
                        company.setId(0L);
                        return Mono.just(company);
                    }

                    @Override
                    public Mono<Void> delete(Long companyId) {
                        return Mono.empty();
                    }

                    @Override
                    public Mono<Company> getCompanyByName(String name) {
                        return company.getName().equals(name) ?
                                Mono.just(company) : Mono.empty();
                    }

                    @Override
                    public Mono<Company> findById(Long companyId) {
                        return company.getId().equals(companyId) ?
                                Mono.just(company) : Mono.empty();
                    }
                }
        );
    }

    private InterviewController createStubInterviewController() {
        return new InterviewController(
                new InterviewMWRepository() {
                    @Override
                    public Mono<Interview> save(Interview interview) {
                        interview.setId(0L);
                        return Mono.just(interview);
                    }

                    @Override
                    public Mono<Void> delete(Long interviewId) {
                        return Mono.empty();
                    }

                    @Override
                    public Flux<LocalDateTime> getAvailableTimes(ExpertTimes expertTimes) {
                        return Flux.just(LocalDateTime.parse("2019-12-01T01:00:00", formatter));
                    }

                    @Override
                    public Mono<Interview> findById(Long interviewId) {
                        return interview.getId().equals(interviewId) ?
                                Mono.just(interview) : Mono.empty();
                    }
                }
        );
    }

    private SolutionController createStubSolutionController() {
        return new SolutionController(
                new SolutionMWRepository() {
                    @Override
                    public Mono<Solution> save(Solution solution) {
                        solution.setId(0L);
                        return Mono.just(solution);
                    }

                    @Override
                    public Mono<Void> delete(Long interviewId) {
                        return Mono.empty();
                    }

                    @Override
                    public Mono<Solution> findById(Long solutionId) {
                        return solution.getId().equals(solutionId) ?
                                Mono.just(solution) : Mono.empty();
                    }
                }
        );
    }

    private UserController createStubUserController() {
        return new UserController(
                new UserAGCrudRepository() {
                    @Override
                    public Flux<User> findByEmailAddr(String emailAddr) {
                        return emailAddr.equals("a@b.com") ?
                                Flux.just(user) : Flux.empty();
                    }

                    @Override
                    public Flux<User> findAllCandidates() {
                        return Flux.just(user);
                    }

                    @Override
                    public Flux<User> findCandidatesByCompanyName(String companyName) {
                        return companyName.equals("Company Name") ?
                                Flux.just(user) : Flux.empty();
                    }

                    @Override
                    public <S extends User> Mono<S> save(S entity) {
                        return null;
                    }

                    @Override
                    public <S extends User> Flux<S> saveAll(Iterable<S> entities) {
                        return null;
                    }

                    @Override
                    public <S extends User> Flux<S> saveAll(Publisher<S> entityStream) {
                        return null;
                    }

                    @Override
                    public Mono<User> findById(Long aLong) {
                        return user.getId().equals(aLong) ?
                                Mono.just(user) : Mono.empty();
                    }

                    @Override
                    public Mono<User> findById(Publisher<Long> id) {
                        return null;
                    }

                    @Override
                    public Mono<Boolean> existsById(Long aLong) {
                        return null;
                    }

                    @Override
                    public Mono<Boolean> existsById(Publisher<Long> id) {
                        return null;
                    }

                    @Override
                    public Flux<User> findAll() {
                        return null;
                    }

                    @Override
                    public Flux<User> findAllById(Iterable<Long> longs) {
                        return null;
                    }

                    @Override
                    public Flux<User> findAllById(Publisher<Long> idStream) {
                        return null;
                    }

                    @Override
                    public Mono<Long> count() {
                        return null;
                    }

                    @Override
                    public Mono<Void> deleteById(Long aLong) {
                        return null;
                    }

                    @Override
                    public Mono<Void> deleteById(Publisher<Long> id) {
                        return null;
                    }

                    @Override
                    public Mono<Void> delete(User entity) {
                        return null;
                    }

                    @Override
                    public Mono<Void> deleteAll(Iterable<? extends User> entities) {
                        return null;
                    }

                    @Override
                    public Mono<Void> deleteAll(Publisher<? extends User> entityStream) {
                        return null;
                    }

                    @Override
                    public Mono<Void> deleteAll() {
                        return null;
                    }
                },
                new UserMWRepository() {
                    @Override
                    public Mono<User> save(User user) {
                        user.setId(0L);
                        return Mono.just(user);
                    }

                    @Override
                    public Mono<Void> delete(Long userId) {
                        return Mono.empty();
                    }

                    @Override
                    public Mono<User> findById(Long userId) {
                        return user.getId().equals(userId) ?
                                Mono.just(user) : Mono.empty();
                    }

                    @Override
                    public Flux<User> findAllCandidatesByExpertise(Set<String> keywords) {
                        return user.getExpertises().stream()
                                .anyMatch(expertise -> expertise.getKeywords().containsAll(keywords)) ?
                                Flux.just(user) : Flux.empty();
                    }

                    @Override
                    public Flux<User> findAllExpertsByExpertise(Set<String> keywords) {
                        return user.getExpertises().stream()
                                .anyMatch(expertise -> expertise.getKeywords().containsAll(keywords)) ?
                                Flux.just(user) : Flux.empty();
                    }

                    @Override
                    public Flux<User> findAllUsersByExpertise(Set<String> keywords, User.ClientType clientType) {
                        return user.getExpertises().stream()
                                .anyMatch(expertise -> expertise.getKeywords().containsAll(keywords)) ?
                                Flux.just(user) : Flux.empty();
                    }
                }
        );
    }

    private VacancyController createStubVacancyController() {
        return new VacancyController(
                new VacancyMWRepository() {
                    @Override
                    public Mono<Vacancy> save(Vacancy vacancy) {
                        vacancy.setId(0L);
                        return Mono.just(vacancy);
                    }

                    @Override
                    public Mono<Void> delete(Long vacancyId) {
                        return Mono.empty();
                    }

                    @Override
                    public Mono<Vacancy> findById(Long vacancyId) {
                        return vacancy.getId().equals(vacancyId) ?
                                Mono.just(vacancy) : Mono.empty();
                    }
                }
        );
    }
}

class MappingJacksonFactory implements Jackson2ObjectMapperFactory {

    @Override
    public ObjectMapper create(Type cls, String charset) {
        return InterviewRestBase.OBJECT_MAPPER;
    }
}