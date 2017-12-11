package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseParticipants extends BaseResponse {
    @Expose
    @SerializedName("data")
    private ArrayList<CaravanParticipants> data;

    public ArrayList<CaravanParticipants> getData() {
        return data;
    }
}
