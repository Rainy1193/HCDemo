package com.homecaravan.android.models;

/**
 * Created by vankhoadesign on 8/25/16.
 */
public class EventResuggest {
    private String id;
    private String time;
    private String start;
    private String end;
    private String date;
    public void add(String id, String time, String start, String end, String date){
        this.id = id;
        this.time = time;
        this.start = start;
        this.end = end;
        this.date = date;
    }
    public void add (String time, String start,String end, String date){
        this.time = time;
        this.start = start;
        this.end = end;
        this.date = date;
    }
    public void add(String time){
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EventResuggest{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
