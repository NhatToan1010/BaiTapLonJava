package com.acer.baitaplonjava.model;

import androidx.annotation.NonNull;

public class SubjectMark {
    private String Subject;
    private float Mark;

    public SubjectMark(String subject, float mark) {
        Subject = subject;
        Mark = mark;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public float getMark() {
        return Mark;
    }

    public void setMark(float mark) {
        Mark = mark;
    }

    @NonNull
    @Override
    public String toString() {
        return ""+Mark;
    }
}
