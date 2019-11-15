package io.voteofconf.tracker.config;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;
import com.github.jasync.sql.db.mysql.util.URLParser;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.voteofconf.tracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Profile("native")
@EnableR2dbcRepositories(basePackages = "io.voteofconf.tracker.repository")
@Configuration
@PropertySource("classpath:mysql.yml")
public class DataSourceConfig extends AbstractR2dbcConfiguration {

    private static final String MYSQL_DRIVER = "mysql";

    @Value("${mysql.host}")
    private String host;

    @Value("${mysql.port}")
    private Integer port;

    @Value("${mysql.database}")
    private String database;

    @Value("${mysql.username}")
    private String username;

    @Value("${mysql.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions.Builder ob = ConnectionFactoryOptions.builder()
                .option(HOST, host)
                .option(PORT, port)
                .option(DATABASE, database)
                .option(DRIVER, MYSQL_DRIVER)
                .option(USER, username)
                .option(PASSWORD, password);
        return ConnectionFactories.get(ob.build());
    }
}

//@EnableR2dbcRepositories
//@Configuration
//class MySqlR2dbConfiguration extends AbstractR2dbcConfiguration {
//
//    @Override
//    public ConnectionFactory connectionFactory() {
//            String url = "mysql://orders:orders@127.0.0.1:3306/orders";
//            return new JasyncConnectionFactory(new MySQLConnectionFactory(
//                    URLParser.INSTANCE.parseOrDie(url, StandardCharsets.UTF_8)));
//        }
//}


