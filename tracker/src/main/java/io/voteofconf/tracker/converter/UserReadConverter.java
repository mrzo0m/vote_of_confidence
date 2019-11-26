package io.voteofconf.tracker.converter;

import io.r2dbc.spi.Row;
import io.voteofconf.tracker.model.AccountType;
import io.voteofconf.tracker.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.relational.core.sql.In;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;

public class UserReadConverter implements Converter<Row, User> {

    @Override
    public User convert(Row source) {
        AccountType accountType;
        try {
            accountType =new AccountType(
                    source.get("accountType_id", Long.class),
                    source.get("accountType_name", String.class),
                    source.get("accountType_description", String.class),
                    source.get("accountType_period", Integer.class),
                    source.get("accountType_cost", BigDecimal.class));
        } catch (Exception ex) {
            accountType = null;
        }

        User.ClientType clientType = null;
        try {
            clientType = User.ClientType.valueOf(Objects.requireNonNull(source.get("client_type", String.class)));
        } catch (Exception ex) {
            clientType = User.ClientType.valueOf(Objects.requireNonNull(source.get("client_type_id", Integer.class)));
        }

        Boolean agreed;
        try {
            Integer val = Objects.requireNonNull(source.get("agreed", Integer.class));
            agreed = val == 1;
        } catch (Exception ex) {
            agreed = null;
        }

        return new User(
                source.get("id", Long.class),
                clientType,
                accountType,
                source.get("first_name", String.class),
                source.get("second_name", String.class),
                source.get("sur_name", String.class),
                source.get("email_addr", String.class),
                agreed,
                new HashSet<>()
        );
    }
}
