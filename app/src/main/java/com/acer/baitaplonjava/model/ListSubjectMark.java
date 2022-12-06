package com.acer.baitaplonjava.model;

public class ListSubjectMark {
    String subName;
    Float subMark;

    public ListSubjectMark(String subName, Float subMark) {
        this.subName = subName;
        this.subMark = subMark;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Float getSubMark() {
        return subMark;
    }

    public void setSubMark(Float subMark) {
        this.subMark = subMark;
    }
}
