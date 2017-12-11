package com.homecaravan.android.consumer.model;


public class TimeBookSingle {
    private String status;
    private String time;
    private String timeEnd;
    private boolean selected;
    private String timeBookStart;
    private String timeBookEnd;
    private String fullTimeBookStart;
    private String fullTimeBookEnd;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimeBookStart() {
        return timeBookStart;
    }

    public void setTimeBookStart(String timeBookStart) {
        this.timeBookStart = timeBookStart;
    }

    public String getTimeBookEnd() {
        return timeBookEnd;
    }

    public void setTimeBookEnd(String timeBookEnd) {
        this.timeBookEnd = timeBookEnd;
    }

    public String getFullTimeBookStart() {
        return fullTimeBookStart;
    }

    public void setFullTimeBookStart(String fullTimeBookStart) {
        this.fullTimeBookStart = fullTimeBookStart;
    }

    public String getFullTimeBookEnd() {
        return fullTimeBookEnd;
    }

    public void setFullTimeBookEnd(String fullTimeBookEnd) {
        this.fullTimeBookEnd = fullTimeBookEnd;
    }
}
