package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUser extends BaseResponse {
    @Expose
    @SerializedName("data")
    private User userData;

    public User getUserData() {
        return userData;
    }

    @Override
    public String toString() {
        return "ResponseUser{" +
                "userData=" + userData +
                '}';
    }
}
