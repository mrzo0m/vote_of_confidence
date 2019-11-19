package io.voteofconf.tracker.exception;

import org.springframework.dao.DataAccessException;

public class R2dbcException extends io.r2dbc.spi.R2dbcException {
    public R2dbcException(String msg) {
        super(msg);
    }

    public R2dbcException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
