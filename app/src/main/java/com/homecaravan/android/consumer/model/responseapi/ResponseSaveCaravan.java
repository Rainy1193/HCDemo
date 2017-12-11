package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseSaveCaravan extends BaseResponse {
    @Expose
    @SerializedName("data")
    ArrayList<CaravanJson> caravanJsons;

    public ArrayList<CaravanJson> getCaravanJsons() {
        return caravanJsons;
    }

    public void setCaravanJsons(ArrayList<CaravanJson> caravanJsons) {
        this.caravanJsons = caravanJsons;
    }
}
