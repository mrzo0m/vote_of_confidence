package io.voteofconf.tracker.converter;

import io.voteofconf.common.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.SettableValue;

@WritingConverter
public class UserWriteConverter implements Converter<User, OutboundRow> {
    @Override
    public OutboundRow convert(User user) {
        OutboundRow row = new OutboundRow();
        if (user.getId() != null)
            row.put("id", SettableValue.from(user.getId()));
        row.put("client_type_id", SettableValue.from(user.getClientType().getId()));
        row.put("account_type_id", SettableValue.from(user.getAccountType().getId()));
        row.put("first_name", SettableValue.from(user.getFirstName()));
        row.put("second_name", SettableValue.from(user.getSecondName()));
        row.put("sur_name", SettableValue.from(user.getSurName()));
        row.put("email_addr", SettableValue.from(user.getEmailAddr()));
        return row;
    }
}
