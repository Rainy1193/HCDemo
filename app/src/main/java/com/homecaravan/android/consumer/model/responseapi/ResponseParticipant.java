package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseParticipant {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("data")
    @Expose
    private ArrayList<Participant> data;

    public String getTotal() {
        return total;
    }

    public ArrayList<Participant> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseParticipant{" +
                "total='" + total + '\'' +
                ", data=" + data +
                '}';
    }
}
