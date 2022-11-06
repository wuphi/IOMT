package com.example.stressmessungpuls.database;

import androidx.room.*;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE user_name LIKE :uname AND " +
            "email LIKE :email LIMIT 1")
    User findByName(String uname, String email);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}