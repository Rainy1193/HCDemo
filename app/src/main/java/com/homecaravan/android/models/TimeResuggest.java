package com.homecaravan.android.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by RAINY on 7/6/2016.
 */
public class TimeResuggest implements Comparable<TimeResuggest> {
    private String day;
    private ArrayList<EventResuggest> timeEvents;
    private int duration;
    private boolean click;

    public TimeResuggest(String day, ArrayList<EventResuggest> timeEvents, int duration) {
        this.day = day;
        this.timeEvents = timeEvents;
        this.duration = duration;

    }

    public ArrayList<EventResuggest> getTimeEvents() {
        return timeEvents;
    }

    public void setTimeEvents(ArrayList<EventResuggest> timeEvents) {
        this.timeEvents = timeEvents;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    @Override
    public int compareTo(TimeResuggest another) {
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

    @Override
    public String toString() {
        return "TimeResuggest{" +
                "day='" + day + '\'' +
                ", timeEvents=" + timeEvents +
                ", duration=" + duration +
                ", click=" + click +
                '}';
    }
}
