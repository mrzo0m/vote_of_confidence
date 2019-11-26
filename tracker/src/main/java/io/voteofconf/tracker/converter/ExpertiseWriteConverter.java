package io.voteofconf.tracker.converter;

import io.voteofconf.common.model.Expertise;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.SettableValue;

@WritingConverter
public class ExpertiseWriteConverter implements Converter<Expertise, OutboundRow> {
    @Override
    public OutboundRow convert(Expertise source) {
        OutboundRow row = new OutboundRow();
        row.put("id", SettableValue.from(source.getId()));
        row.put("name", SettableValue.from(source.getName()));
        row.put("keywords", SettableValue.from(String.join(",", source.getKeywords())));
        row.put("description", SettableValue.from(source.getDescription()));
        row.put("level", SettableValue.from(source.getLevel().ordinal()));
        return row;
    }
}
