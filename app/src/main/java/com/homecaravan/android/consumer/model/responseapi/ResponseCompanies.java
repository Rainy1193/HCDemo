package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 11/15/2017.
 */

public class ResponseCompanies extends BaseResponse {

    @Expose
    @SerializedName("data")
    private ArrayList<Companies> companies;

    public ArrayList<Companies> getCompanies() {
        return companies;
    }

}
