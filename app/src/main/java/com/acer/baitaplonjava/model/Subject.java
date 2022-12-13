package com.acer.baitaplonjava.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "SUBJECT")
public class Subject implements Serializable {

    @PrimaryKey
    @NonNull
    private String subID;
    private String subName;
    public Subject() {
    }

    public Subject(String subID, String subName) {
        this.subID = subID;
        this.subName = subName;
    }

    public String getSubID() {
        return subID;
    }

    public void setSubID(String subID) {
        this.subID = subID;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
