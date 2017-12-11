package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseConsumerMapSearch {
    @SerializedName("datas")
    @Expose
    private ArrayList<ConsumerMapSearch> mapSearches;

    public ArrayList<ConsumerMapSearch> getMapSearches() {
        return mapSearches;
    }
}
