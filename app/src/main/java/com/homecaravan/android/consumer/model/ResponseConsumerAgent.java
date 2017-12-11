package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseConsumerAgent {
    @SerializedName("agents")
    @Expose
    private ArrayList<ConsumerAgent> agents;

    public ArrayList<ConsumerAgent> getAgents() {
        return agents;
    }
}
