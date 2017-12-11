package com.homecaravan.android.consumer.model;

public class DayOfWeekly {
    private String status;

    private boolean selected;
    private String timeBookStart;
    private String timeBookEnd;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    @Override
    public String toString() {
        return "DayOfWeekly{" +
                "status='" + status + '\'' +
                ", selected=" + selected +
                ", timeBookStart='" + timeBookStart + '\'' +
                ", timeBookEnd='" + timeBookEnd + '\'' +
                '}';
    }
}
