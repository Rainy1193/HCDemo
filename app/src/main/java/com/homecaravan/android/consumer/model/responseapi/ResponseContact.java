package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseContact extends BaseResponse {
    @SerializedName("data")
    @Expose
    private ContactData data;

    public ContactData getData() {
        return data;
    }
}
