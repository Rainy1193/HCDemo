package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 6/3/2016.
 */
public class ResponseChangeStatus {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ChangeStatus data;
    @SerializedName("code")
    @Expose
    private int code;

    public String isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ChangeStatus getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public class ChangeStatus
    {
        @SerializedName("next_status")
        @Expose
        private String next;

        public String getNext() {
            return next;
        }
    }
}
