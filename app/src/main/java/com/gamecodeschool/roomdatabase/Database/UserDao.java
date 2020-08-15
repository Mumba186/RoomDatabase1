package com.gamecodeschool.roomdatabase.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from users WHERE userId=(:userId) and password=(:password)")
    UserEntity login(String userId, String password);

    @Query("DELETE FROM users")
    void deleteAll();
}
