package com.homecaravan.android.consumer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseConsumerListing {
    @SerializedName("datas")
    @Expose
    private ArrayList<ConsumerListing> listings;

    public ArrayList<ConsumerListing> getListings() {
        return listings;
    }
}
