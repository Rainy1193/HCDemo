package com.homecaravan.android.consumer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseConsumerHome {
    @SerializedName("datas")
    @Expose
    private ArrayList<ConsumerHome> homes;

    public ArrayList<ConsumerHome> getHomes() {
        return homes;
    }
}
