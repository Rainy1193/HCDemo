package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anh Dao on 10/24/2017.
 */

public class ResponseMessageGetThreadId {
    @Expose
    @SerializedName("success")
    private boolean success;
    @Expose
    @SerializedName("thread")
    private String threadId;

    public boolean isSuccess() {
        return success;
    }

    public String getThreadId() {
        return threadId;
    }
}
