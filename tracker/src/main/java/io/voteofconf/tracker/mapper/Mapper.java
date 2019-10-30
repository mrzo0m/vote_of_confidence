package io.voteofconf.tracker.mapper;

import com.github.jasync.sql.db.general.ArrayRowData;
import io.voteofconf.tracker.model.Entity;
import io.voteofconf.tracker.model.User;

public interface Mapper<T extends Entity> {
    T map(ArrayRowData data);
}
