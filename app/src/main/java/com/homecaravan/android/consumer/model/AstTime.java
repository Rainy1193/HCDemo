package com.homecaravan.android.consumer.model;

public class AstTime {
    private String time;
    private boolean night;

    public AstTime(String time, boolean night) {
        this.time = time;
        this.night = night;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    @Override
    public String toString() {
        return "AstTime{" +
                "time='" + time + '\'' +
                ", night=" + night +
                '}';
    }
}