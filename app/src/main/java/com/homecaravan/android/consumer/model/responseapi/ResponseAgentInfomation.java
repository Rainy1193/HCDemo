package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anh Dao on 11/3/2017.
 */

public class ResponseAgentInfomation extends BaseResponse {
    @SerializedName("data")
    @Expose
    private User data;

    public User getData() {
        return data;
    }
}
