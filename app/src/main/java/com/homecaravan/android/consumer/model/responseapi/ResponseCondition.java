package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseCondition {

    @SerializedName("data")
    @Expose
    private ArrayList<Condition> data;

    public ArrayList<Condition> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseCondition{" +
                "data=" + data +
                '}';
    }
}
