package com.acer.baitaplonjava.helpers;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    private static final String PATTERN = "dd/MM/yyyy";

    public static Date toDate(String st) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        return sdf.parse(st);
    }

    public static String toString(Date date){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        return sdf.format(date);
    }
}
