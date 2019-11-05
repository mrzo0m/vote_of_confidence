package io.voteofconf.tracker.model;

import com.github.jasync.sql.db.general.ArrayRowData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Entity {
    private String fisrtName;
    private String secondName;
    private String surName;
    private String emailAddr;


    public User(ArrayRowData data) {
        fisrtName = data.getString(0);
        secondName = data.getString(1);
        surName = data.getString(2);
        emailAddr = data.getString(3);
    }
}
