package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseConditionSearchDetail {
    @SerializedName("data")
    @Expose
    private ArrayList<ConditionFull> data;

    public ArrayList<ConditionFull> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseConditionSearchDetail{" +
                "data=" + data +
                '}';
    }
}
