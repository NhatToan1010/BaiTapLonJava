package com.acer.baitaplonjava.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.acer.baitaplonjava.model.Account;

import java.util.List;

@Dao
public interface AccountDAO {

    @Insert
    void insert(Account acc);

    @Update
    void update(Account acc);

    @Delete
    void delete(Account account);

    @Query("DELETE FROM ACCOUNT")
    void deleteAll();

    @Query("SELECT * FROM ACCOUNT")
    LiveData<List<Account>> findAll();

    @Query("SELECT * FROM ACCOUNT WHERE username = :name")
    List<Account> checkUsername(String name);

    @Query("SELECT username FROM ACCOUNT")
    String findUsername();

    @Query("SELECT password FROM ACCOUNT")
    String findPassword();
}
