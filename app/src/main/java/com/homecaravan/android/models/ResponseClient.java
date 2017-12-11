package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 5/20/2016.
 */
public class ResponseClient {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<Client> arrClinet;
    @SerializedName("code")
    @Expose
    private String code;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Client> getArrClinet() {
        return arrClinet;
    }

    public String getCode() {
        return code;
    }


}
