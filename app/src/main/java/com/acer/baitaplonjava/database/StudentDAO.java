package com.acer.baitaplonjava.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.acer.baitaplonjava.model.Student;

import java.util.List;

@Dao
public interface StudentDAO {

    @Insert
    void insert(Student stu);

    @Update
    void update(Student stu);

    @Delete
    void delete(Student stu);

    @Query("DELETE FROM STUDENT")
    void deleteAll();

    @Query("SELECT * FROM STUDENT")
    LiveData<List<Student>> findAll();

    @Query("SELECT * FROM STUDENT WHERE ID = :id")
    List<Student> checkID(String id);
}
