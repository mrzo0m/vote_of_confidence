package io.voteofconf.tracker.converter;

import io.voteofconf.common.model.Interview;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.SettableValue;

@WritingConverter
public class InterviewWriteConverter implements Converter<Interview, OutboundRow> {

    @Override
    public OutboundRow convert(Interview source) {
        OutboundRow row = new OutboundRow();
        row.put("id", SettableValue.from(source.getId()));
        row.put("candidate_id", SettableValue.from(source.getCandidate().getId()));
        row.put("expert_id", SettableValue.from(source.getExpert().getId()));
        row.put("discipline_id", SettableValue.from(source.getDiscipline().getId()));
        row.put("date_time", SettableValue.from(source.getDateOfCreation()));
        row.put("date_of_interview", SettableValue.from(source.getDateOfInterview()));
        row.put("calendly_link", SettableValue.from(source.getCalendlyLink()));
        return row;
    }
}
