package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseListing {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("data")
    @Expose
    private ArrayList<Listing> data;

    public String getTotal() {
        return total;
    }

    public ArrayList<Listing> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseListing{" +
                "total='" + total + '\'' +
                ", data=" + data +
                '}';
    }
}
