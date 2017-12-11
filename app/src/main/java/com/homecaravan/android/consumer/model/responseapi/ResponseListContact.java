package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseListContact extends BaseResponse {
    @SerializedName("data")
    @Expose
    private ArrayList<ContactData> data;

    public ArrayList<ContactData> getData() {
        return data;
    }
}