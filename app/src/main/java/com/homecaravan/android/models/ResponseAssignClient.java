package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vankhoadesign on 7/3/16.
 */
public class ResponseAssignClient {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("data")
    @Expose
    private InviteReturn data;

    public String isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
    public InviteReturn getData() {
        return data;
    }
}
