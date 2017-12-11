package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 5/3/2016.
 */
public class DaySlot {

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("DAY_SLOT")
    @Expose
    private String daySlot;
    @SerializedName("STATUS")
    @Expose
    private String status;
    @SerializedName("TIME_SLOTS")
    @Expose
    private ArrayList<TimeSlot> arrTimeSlot;

    public String getId() {
        return id;
    }

    public String getDaySlot() {
        return daySlot;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<TimeSlot> getArrTimeSlot() {
        return arrTimeSlot;
    }
}
