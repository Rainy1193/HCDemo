package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseShowingCaravan extends BaseResponse {
    @Expose
    @SerializedName("data")
    ArrayList<CaravanShowing> caravanShowings;

    public ArrayList<CaravanShowing> getCaravanShowings() {
        return caravanShowings;
    }
}
