package io.voteofconf.tracker.model;

import com.github.jasync.sql.db.general.ArrayRowData;
import lombok.Data;

@Data
public class User extends Entity {
   private String fisrtName;
   private String secondName;
   private String surName;
   private String emailAddr;

    public User(ArrayRowData data) {
        fisrtName = data.getString(1);
        secondName = data.getString(2);
        surName = data.getString(3);
        emailAddr = data.getString(4);
    }
}
