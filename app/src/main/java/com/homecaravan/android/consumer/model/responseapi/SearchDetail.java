package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchDetail {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("topTrend")
    @Expose
    private String topTrend;
    @SerializedName("myRole")
    @Expose
    private String myRole;
    @SerializedName("newAccount")
    @Expose
    private String newAccount;
    @SerializedName("participants")
    @Expose
    private ResponseParticipant participants;
    @SerializedName("listings")
    @Expose
    private ResponseListingSearchDetail listings;
    @SerializedName("conditions")
    @Expose
    private ArrayList<ConditionFull> conditions;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTopTrend() {
        return topTrend;
    }

    public String getMyRole() {
        return myRole;
    }

    public String getNewAccount() {
        return newAccount;
    }

    public ResponseParticipant getParticipants() {
        return participants;
    }

    public ResponseListingSearchDetail getListings() {
        return listings;
    }

    public  ArrayList<ConditionFull> getConditions() {
        return conditions;
    }

    @Override
    public String toString() {
        return "SearchDetail{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", topTrend='" + topTrend + '\'' +
                ", myRole='" + myRole + '\'' +
                ", newAccount='" + newAccount + '\'' +
                ", participants=" + participants +
                ", listings=" + listings +
                ", conditions=" + conditions +
                '}';
    }
}
