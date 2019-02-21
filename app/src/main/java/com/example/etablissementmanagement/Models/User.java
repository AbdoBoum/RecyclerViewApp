package com.example.etablissementmanagement.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "user_table")
public class User implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "user_name")
    private String login;

    @ColumnInfo(name = "user_password")
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
