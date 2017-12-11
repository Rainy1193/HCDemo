package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BaseResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("messages")
    @Expose
    private ArrayList<BaseResponseMessages> messages;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<BaseResponseMessages> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", code='" + code + '\'' +
                ", messages=" + messages +
                '}';
    }
}
