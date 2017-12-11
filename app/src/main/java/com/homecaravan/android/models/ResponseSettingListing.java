package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 5/30/2016.
 */
public class ResponseSettingListing {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Listing data;
    @SerializedName("code")
    @Expose
    private String code;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Listing getData() {
        return data;
    }

    public String getCode() {
        return code;
    }
}
