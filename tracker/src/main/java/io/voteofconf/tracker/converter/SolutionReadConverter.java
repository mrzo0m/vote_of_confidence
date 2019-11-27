package io.voteofconf.tracker.converter;

import io.r2dbc.spi.Row;
import io.voteofconf.common.model.Solution;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Objects;

@ReadingConverter
public class SolutionReadConverter implements Converter<Row, Solution> {

    @Override
    public Solution convert(Row source) {

        Solution.ResolutionType resolutionType = null;
        try {
            resolutionType = Solution.ResolutionType.valueOf(Objects.requireNonNull(source.get("resolution_id", Integer.class)));
        } catch (Exception ex) {
            resolutionType = Solution.ResolutionType.valueOf(Objects.requireNonNull(source.get("resolution", String.class)));
        }

        return new Solution(
                source.get("id", Long.class),
                resolutionType,
                source.get("interview_application_id", Long.class),
                source.get("report_id", Long.class),
                source.get("certificate_id", Long.class)
        );
    }
}
