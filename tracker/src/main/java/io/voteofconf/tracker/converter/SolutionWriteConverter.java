package io.voteofconf.tracker.converter;

import io.voteofconf.common.model.Solution;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.SettableValue;

@WritingConverter
public class SolutionWriteConverter implements Converter<Solution, OutboundRow> {
    @Override
    public OutboundRow convert(Solution source) {
        OutboundRow row = new OutboundRow();
        row.put("id", SettableValue.from(source.getId()));
        row.put("resolution_id", SettableValue.from(source.getResolutionType().getId()));
        row.put("interview_application_id", SettableValue.from(source.getInterview().getId()));
        row.put("report_id", SettableValue.from(source.getCertificate().getId()));
        row.put("certificate_id", SettableValue.from(source.getReport().getId()));
        return row;
    }
}
