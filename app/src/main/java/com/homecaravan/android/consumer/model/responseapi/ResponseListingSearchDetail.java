package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseListingSearchDetail {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("data")
    @Expose
    private
    ArrayList<ListingFull> data;

    public String getTotal() {
        return total;
    }

    public ArrayList<ListingFull> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseListingSearchDetail{" +
                "total='" + total + '\'' +
                ", data=" + data +
                '}';
    }
}
