package io.voteofconf.tracker.mapper;

import com.github.jasync.sql.db.general.ArrayRowData;
import io.voteofconf.tracker.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User> {

    @Override
    public User map(ArrayRowData data) {
        return new User(data);
    }
}
