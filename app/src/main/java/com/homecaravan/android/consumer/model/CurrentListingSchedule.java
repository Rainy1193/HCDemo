package com.homecaravan.android.consumer.model;

import com.homecaravan.android.models.AgentSchedule;

import java.util.ArrayList;

public class CurrentListingSchedule {
    private static CurrentListingSchedule listingSchedule;
    private ArrayList<ConsumerListingSchedule> arrListing = new ArrayList<>();
    private ArrayList<AgentSchedule> agentSchedule = new ArrayList<>();
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private String half;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getHalf() {
        return half;
    }

    public void setHalf(String half) {
        this.half = half;
    }

    public static CurrentListingSchedule getInstance() {
        if (listingSchedule == null) {
            listingSchedule = new CurrentListingSchedule();
        }
        return listingSchedule;
    }

    public ArrayList<ConsumerListingSchedule> getArrListing() {
        return arrListing;
    }

    public void setArrListing(ArrayList<ConsumerListingSchedule> arrListing) {
        this.arrListing = arrListing;
    }

    public ArrayList<AgentSchedule> getAgentSchedule() {
        return agentSchedule;
    }

    public void setAgentSchedule(ArrayList<AgentSchedule> agentRoute) {
        this.agentSchedule = agentRoute;
    }


}
