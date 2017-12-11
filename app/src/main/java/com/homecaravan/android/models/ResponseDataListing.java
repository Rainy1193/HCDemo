package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 4/19/2016.
 */
public class ResponseDataListing {
    @SerializedName("total_rows")
    @Expose
    private String totalRows;
    @SerializedName("listings")
    @Expose
    private ArrayList<Listing> arrListing;

    public String getTotalRows() {
        return totalRows;
    }

    public ArrayList<Listing> getArrListing() {
        return arrListing;
    }


}
