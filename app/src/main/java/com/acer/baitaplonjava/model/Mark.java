package com.acer.baitaplonjava.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "MARK",
        primaryKeys = {"studentID", "subjectID"},
        foreignKeys = {
        @ForeignKey(entity = Student.class,
                    parentColumns = "ID",
                    childColumns = "studentID",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Subject.class,
                    parentColumns = "subID",
                    childColumns = "subjectID",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)}
)
public class Mark implements Serializable {
    @NonNull
    private String studentID;
    @NonNull
    private String subjectID;
    private float subMark;
    public Mark(@NonNull String studentID, @NonNull String subjectID, float subMark) {
        this.studentID = studentID;
        this.subjectID = subjectID;
        this.subMark = subMark;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(@NonNull String studentID) {
        this.studentID = studentID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(@NonNull String subjectID) {
        this.subjectID = subjectID;
    }

    public float getSubMark() {
        return subMark;
    }

    public void setSubMark(float subMark) {
        this.subMark = subMark;
    }
}
