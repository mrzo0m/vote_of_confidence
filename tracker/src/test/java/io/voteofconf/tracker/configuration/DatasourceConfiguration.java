package io.voteofconf.tracker.configuration;

import com.github.jasync.sql.db.Connection;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class DatasourceConfiguration {

    @Bean
    public Connection mysqlDataConnection() {
        return Mockito.mock(Connection.class);
    }
}
