package io.voteofconf.tracker;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
//@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@Transactional
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class TrackerApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	public void contextLoads() throws SQLException {
		ResultSet resultSet = dataSource.getConnection().createStatement().executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema ='PUBLIC'");
		while (resultSet.next()) {
			log.info(String.format(" table %s created", resultSet.getString(1)));
		}
		log.info("finish test");
		Assert.assertTrue(true);
	}
}
