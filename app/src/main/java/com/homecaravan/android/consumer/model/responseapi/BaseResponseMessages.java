package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponseMessages {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("actionType")
    @Expose
    private String actionType;

    public String getText() {
        return text;
    }

    public int getCode() {
        return code;
    }

    public String getActionType() {
        return actionType;
    }

    @Override
    public String toString() {
        return "BaseResponseMessages{" +
                "text='" + text + '\'' +
                ", code='" + code + '\'' +
                ", actionType='" + actionType + '\'' +
                '}';
    }
}
