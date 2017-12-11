package com.homecaravan.android.consumer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseConsumerTeam {
    @SerializedName("teams")
    @Expose
    private ArrayList<ConsumerTeam> teams;

    public ArrayList<ConsumerTeam> getTeams() {
        return teams;
    }
}
