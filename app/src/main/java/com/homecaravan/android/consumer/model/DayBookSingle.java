package com.homecaravan.android.consumer.model;

public class DayBookSingle {
    private String day;
    private boolean select;
    private String status;
    private String fullDay;
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullDay() {
        return fullDay;
    }

    public void setFullDay(String fullDay) {
        this.fullDay = fullDay;
    }
}
