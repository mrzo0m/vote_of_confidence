package io.voteofconf.tracker.converter;

import io.r2dbc.spi.Row;
import io.voteofconf.common.model.Interview;
import io.voteofconf.tracker.repository.UserMWRepositoryImpl;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@ReadingConverter
public class InterviewReadConverter implements Converter<Row, Interview>  {


    private UserMWRepositoryImpl userMWRepository;

    @Override
    public Interview convert(Row source) {
        return new Interview(
                source.get("id", Long.class),
                source.get("candidate_id", Long.class),
                source.get("expert_id", Long.class),
                source.get("discipline_id", Long.class),
                new DateTime(Objects.requireNonNull(source.get("date_time", LocalDateTime.class)).toEpochSecond(ZoneOffset.of("+03:00"))),
                new DateTime(Objects.requireNonNull(source.get("date_time", LocalDateTime.class)).toEpochSecond(ZoneOffset.of("+03:00"))),
                source.get("calendly_link", String.class)
        );
    }
}
