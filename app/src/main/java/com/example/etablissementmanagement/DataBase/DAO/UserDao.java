package com.example.etablissementmanagement.DataBase.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.etablissementmanagement.Models.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * from user_table where user_name = :login and user_password = :password")
    User getUser(String login, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);

    //@Update
    //void updateUser(User user);

    //@Delete
    //public void deleteUser(String login);

    @Query("SELECT * from user_table")
    LiveData<List<User>> getUsers();

}
