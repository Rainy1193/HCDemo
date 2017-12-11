package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 5/3/2016.
 */
public class ListingDetailData {
    @SerializedName("LISTING")
    @Expose
    private ListingDetailDataListing listingDetailDataListing;
    @SerializedName("SETTINGS")
    @Expose
    private ListingDetailDataSetting listingDetailDataSetting;
    @SerializedName("AGENT")
    @Expose
    private ListingDetailDataAgent listingDetailDataAgent;
    @SerializedName("TEAM_LIST")
    @Expose
    private ArrayList<ListingTeam> listingDetailTeam;

    public ListingDetailDataAgent getListingDetailDataAgent() {
        return listingDetailDataAgent;
    }

    public ListingDetailDataListing getListingDetailDataListing() {
        return listingDetailDataListing;
    }

    public ListingDetailDataSetting getListingDetailDataSetting() {
        return listingDetailDataSetting;
    }

    public ArrayList<ListingTeam> getListingDetailTeam() {
        return listingDetailTeam;
    }
}
