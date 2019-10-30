package io.voteofconf.tracker.config;

import com.github.jasync.sql.db.Connection;
import com.github.jasync.sql.db.ConnectionPoolConfigurationBuilder;
import com.github.jasync.sql.db.QueryResult;
import com.github.jasync.sql.db.mysql.MySQLConnectionBuilder;
import kotlin.Unit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.CompletableFuture;

@Configuration
@PropertySource("classpath:mysql.yml")
public class DataSourceConfig {

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
    public Connection mysqlDataConnection() {
//        ConnectionPoolConfigurationBuilder builder = new ConnectionPoolConfigurationBuilder();
//        builder.setMaxActiveConnections(10);
//        builder.setConnectionCreateTimeout(60);
//        builder.setConnectionTestTimeout(60);
//        builder.setMaxConnectionTtl(1800L);

        Connection connection = MySQLConnectionBuilder.createConnectionPool(
                String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", host, port, database, username, password));

//// Execute query
//        CompletableFuture<QueryResult> future = connection.sendPreparedStatement("select * from table");
//// work with result ...
//// Close the connection pool
//        connection.disconnect().get();

        return connection;

    }
}
