package io.voteofconf.tracker.repository;

import com.github.jasync.sql.db.QueryResult;
import com.github.jasync.sql.db.general.ArrayRowData;
import io.voteofconf.tracker.mapper.Mapper;
import io.voteofconf.tracker.model.Entity;

import java.util.List;
import java.util.stream.Collectors;

public class RowDataProcessor {

    public static <T extends Entity> List<T> processData(QueryResult queryResult, Mapper<T> mapper) {
        return queryResult.getRows().stream()
                .map(rowData -> (ArrayRowData)rowData)
                .map(mapper::map)
                .collect(Collectors.toList());
    }
}
