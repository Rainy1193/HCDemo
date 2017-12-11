package com.homecaravan.android.consumer.model;

import java.util.ArrayList;

public class AstDay {
    private String day;
    private ArrayList<String> time;

    public AstDay(String day, ArrayList<String> time) {
        this.day = day;
        this.time = time;
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
    public String toString() {
        return "AstDay{" +
                "day='" + day + '\'' +
                ", time=" + time +
                '}';
    }
}
