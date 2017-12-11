package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseConsumerMessage {
    @SerializedName("datas")
    @Expose
    private ArrayList<ConsumerMessage> messages;

    public ArrayList<ConsumerMessage> getMessages() {
        return messages;
    }
}
