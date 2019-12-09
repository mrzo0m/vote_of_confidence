package io.voteofconf.tracker;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class TrackerApplicationTests {


	@Autowired
	private DatabaseClient databaseClient;

	@Test
	public void contextLoads() throws SQLException {
		databaseClient.execute("SELECT table_name FROM information_schema.tables")
				.fetch()
				.all()
				.doOnNext(map ->
						map.forEach(
								(key, value) -> log.info(String.format(" table %s created", value))))
				.doOnComplete(() -> log.info("finish test"))
				.subscribe(map -> Assert.assertTrue(true));
	}
}
