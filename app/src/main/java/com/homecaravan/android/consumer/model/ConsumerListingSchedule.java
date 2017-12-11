package com.homecaravan.android.consumer.model;

import com.homecaravan.android.consumer.model.responseapi.Listing;

import java.util.Calendar;


public class ConsumerListingSchedule {
    private Listing listing;
    private boolean isStart;
    private boolean isSelect;
    private int position;
    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;
    private String startHaft;
    private String endHaft;
    private int duration;
    private Calendar timeStart;
    private Calendar timeEnd;

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listingFullItem) {
        this.listing = listingFullItem;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStartHaft() {
        return startHaft;
    }

    public void setStartHaft(String startHaft) {
        this.startHaft = startHaft;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public String getEndHaft() {
        return endHaft;
    }

    public void setEndHaft(String endHaft) {
        this.endHaft = endHaft;
    }

    public Calendar getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Calendar timeStart) {
        this.timeStart = timeStart;
    }

    public Calendar getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Calendar timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return "ConsumerListingSchedule{" +
                "listing=" + listing +
                ", isStart=" + isStart +
                ", isSelect=" + isSelect +
                ", position=" + position +
                ", startHour=" + startHour +
                ", startMin=" + startMin +
                ", endHour=" + endHour +
                ", endMin=" + endMin +
                ", startHaft='" + startHaft + '\'' +
                ", endHaft='" + endHaft + '\'' +
                ", duration=" + duration +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                '}';
    }
}
