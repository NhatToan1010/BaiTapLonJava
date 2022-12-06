package com.acer.baitaplonjava.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.acer.baitaplonjava.model.Subject;

import java.util.List;

@Dao
public interface SubjectDAO {

    @Insert
    void insert(Subject sub);

    @Update
    void update(Subject sub);

    @Delete
    void delete(Subject sub);

    @Query("DELETE FROM SUBJECT")
    void deleteAll();

    @Query("SELECT * FROM SUBJECT")
    LiveData<List<Subject>> findAll();

    @Query("SELECT subID FROM SUBJECT WHERE subID = :id")
    List<Subject> getByID(String id);

    @Query("SELECT subID FROM SUBJECT")
    List<String> getID();

    @Query("SELECT subName FROM SUBJECT WHERE subID = :id")
    String findById(String id);

    @Query("SELECT subName FROM SUBJECT sub JOIN MARK m ON sub.subID = m.subjectID WHERE m.studentID = :stuId")
    List<String> getSubNameById(String stuId);

}
