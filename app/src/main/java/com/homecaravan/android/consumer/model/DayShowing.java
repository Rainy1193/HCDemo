package com.homecaravan.android.consumer.model;


public class DayShowing {

    private String day;
    private boolean select;
    private boolean inMonth;
    private String th;
    private String fullDay;
    public String getDay() {
        return day;
    }
    private boolean showPending;
    private boolean showApproved;
    private boolean showCancelled;

    public boolean isShowPending() {
        return showPending;
    }

    public void setShowPending(boolean showPending) {
        this.showPending = showPending;
    }

    public boolean isShowApproved() {
        return showApproved;
    }

    public void setShowApproved(boolean showApproved) {
        this.showApproved = showApproved;
    }

    public boolean isShowCancelled() {
        return showCancelled;
    }

    public void setShowCancelled(boolean showCancelled) {
        this.showCancelled = showCancelled;
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

    public boolean isInMonth() {
        return inMonth;
    }

    public void setInMonth(boolean inMonth) {
        this.inMonth = inMonth;
    }

    public String getTh() {
        return th;
    }

    public void setTh(String th) {
        this.th = th;
    }

    public String getFullDay() {
        return fullDay;
    }

    public void setFullDay(String fullDay) {
        this.fullDay = fullDay;
    }
}
