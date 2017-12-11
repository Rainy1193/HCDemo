package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseConsumerUpcoming {
    @SerializedName("datas")
    @Expose
    private ArrayList<ConsumerUpcoming> upcomings;

    public ArrayList<ConsumerUpcoming> getUpcomings() {
        return upcomings;
    }
}
