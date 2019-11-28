package io.voteofconf.tracker.converter;

import io.r2dbc.spi.Row;
import io.voteofconf.common.model.Expertise;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

@ReadingConverter
public class ExpertiseReadConverter implements Converter<Row, Expertise> {
    @Override
    public Expertise convert(Row source) {
    Expertise exp = new Expertise(source.get("id", Long.class),
            source.get("name", String.class),
            new HashSet<>(Arrays.asList(
                    Objects.requireNonNull(source.get("keywords", String.class))
                            .split(","))),
            source.get("description", String.class),
            Expertise.ExpertiseLevel.LEVEL_0);


            switch(Objects.requireNonNull(source.get("level", Integer.class))) {
                case 1:
                    exp.setLevel(Expertise.ExpertiseLevel.LEVEL_1);
                    break;
                case 2:
                    exp.setLevel(Expertise.ExpertiseLevel.LEVEL_2);
                    break;
                case 3:
                    exp.setLevel(Expertise.ExpertiseLevel.LEVEL_3);
                    break;
            }

            return exp;
    }
}
