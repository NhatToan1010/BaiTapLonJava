package com.acer.baitaplonjava.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "STUDENT")
public class Student implements Serializable {

    @PrimaryKey
    @NonNull
    private String ID;
    @NonNull
    private String name;
    private boolean gender;
    private String major;
    private String address;
    private Date dob;

    public Student(String ID, String name, boolean gender, String major, String address, Date dob) {
        this.ID = ID;
        this.name = name;
        this.gender = gender;
        this.major = major;
        this.address = address;
        this.dob = dob;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
