package com.homecaravan.android.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by RAINY on 7/6/2016.
 */
public class TimeBookAst implements Comparable<TimeBookAst>{
    private String day;
    private ArrayList<String> time;

    private boolean click;
    public TimeBookAst(String day, ArrayList<String> time,int duration) {
        this.day = day;
        this.time = time;
        this.duration = duration;

    }

    private   int duration;


    public  int getDuration() {
        return duration;
    }

    public  void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }

    @Override
    public int compareTo(TimeBookAst another) {
        int check = 0;
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = parseFormat.parse(this.getDay());
            Date date2 = parseFormat.parse(another.getDay());
            check = date.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return check;
    }
}
