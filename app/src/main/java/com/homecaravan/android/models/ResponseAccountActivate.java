package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 3/28/2016.
 */
public class ResponseAccountActivate {


    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("code")
    @Expose
    private int code;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    public int getCode() {
        return code;
    }
}
