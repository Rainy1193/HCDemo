package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 5/3/2016.
 */
public class ListingDetailDataSetting {
    @SerializedName("DAYSLOT")
    @Expose
    private ArrayList<DaySlot> daySlots;

    public ArrayList<DaySlot> getDaySlots() {
        return daySlots;
    }
}
