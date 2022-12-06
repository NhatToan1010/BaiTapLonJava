package com.acer.baitaplonjava.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.acer.baitaplonjava.model.Mark;
import com.acer.baitaplonjava.model.Subject;

import java.util.List;

@Dao
public interface MarkDAO {

    @Insert
    void insert(Mark mark);

    @Update
    void update(Mark mark);

    @Delete
    void delete(Mark mark);

    @Query("DELETE FROM MARK")
    void deleteAll();

    @Query("SELECT * FROM MARK")
    LiveData<List<Mark>> findAll();

    @Query("SELECT subMark FROM SUBJECT sub JOIN MARK m ON sub.subID = m.subjectID WHERE m.studentID = :stuId")
    List<Float> getMarkById(String stuId);

    @Query("SELECT COUNT(*) FROM SUBJECT sub JOIN MARK mark ON sub.subID = mark.subjectID WHERE mark.studentID = :stuId")
    int getCountOfMark(String stuId);

    @Query("SELECT AVG(subMark) FROM MARK WHERE studentID = :stuId")
    float getAVGMark(String stuId);
}
