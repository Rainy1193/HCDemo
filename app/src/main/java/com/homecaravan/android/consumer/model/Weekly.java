package com.homecaravan.android.consumer.model;


public class Weekly {
    private String hour;
    private DayOfWeekly sun;
    private DayOfWeekly mon;
    private DayOfWeekly tue;
    private DayOfWeekly wed;
    private DayOfWeekly thu;
    private DayOfWeekly fri;
    private DayOfWeekly sat;
    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public DayOfWeekly getSun() {
        return sun;
    }

    public void setSun(DayOfWeekly sun) {
        this.sun = sun;
    }

    public DayOfWeekly getMon() {
        return mon;
    }

    public void setMon(DayOfWeekly mon) {
        this.mon = mon;
    }

    public DayOfWeekly getTue() {
        return tue;
    }

    public void setTue(DayOfWeekly tue) {
        this.tue = tue;
    }

    public DayOfWeekly getWed() {
        return wed;
    }

    public void setWed(DayOfWeekly wed) {
        this.wed = wed;
    }

    public DayOfWeekly getThu() {
        return thu;
    }

    public void setThu(DayOfWeekly thu) {
        this.thu = thu;
    }

    public DayOfWeekly getFri() {
        return fri;
    }

    public void setFri(DayOfWeekly fri) {
        this.fri = fri;
    }

    public DayOfWeekly getSat() {
        return sat;
    }

    public void setSat(DayOfWeekly sat) {
        this.sat = sat;
    }

    @Override
    public String toString() {
        return "Weekly{" +
                "hour='" + hour + '\'' +
                ", sun=" + sun +
                ", mon=" + mon +
                ", tue=" + tue +
                ", wed=" + wed +
                ", thu=" + thu +
                ", fri=" + fri +
                ", sat=" + sat +
                '}';
    }
}
