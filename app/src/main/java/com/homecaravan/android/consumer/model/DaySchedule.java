package com.homecaravan.android.consumer.model;

public class DaySchedule {
    private String day;
    private boolean select;
    private boolean visible;
    private boolean inMonth;

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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isInMonth() {
        return inMonth;
    }

    public void setInMonth(boolean inMonth) {
        this.inMonth = inMonth;
    }

    @Override
    public String toString() {
        return "DaySchedule{" +
                "day='" + day + '\'' +
                ", select=" + select +
                ", visible=" + visible +
                ", inMonth=" + inMonth +
                '}';
    }
}
