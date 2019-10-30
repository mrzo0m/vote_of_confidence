package io.voteofconf.tracker.repository;

import com.github.jasync.sql.db.Connection;
import com.github.jasync.sql.db.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class UserDao {

    private Connection connection;

    @Autowired
    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public CompletableFuture<QueryResult> getUsers() {
        return connection.sendPreparedStatement("select * from user");
    }
}
