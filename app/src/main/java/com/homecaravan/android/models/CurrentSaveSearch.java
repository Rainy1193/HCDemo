package com.homecaravan.android.models;

import com.homecaravan.android.consumer.model.responseapi.ListingFull;
import com.homecaravan.android.consumer.model.responseapi.Participant;
import com.homecaravan.android.consumer.model.responseapi.SearchDetail;

import java.util.ArrayList;

public class CurrentSaveSearch {
    public static CurrentSaveSearch mCurrentSaveSearch;
    private ArrayList<Participant> mArrParticipant=new ArrayList<>();
    private ArrayList<ListingFull> mArrListing=new ArrayList<>();
    private String mName;
    private SearchDetail mSearchDetail;
    private String mId;

    public static CurrentSaveSearch getInstance() {
        if (mCurrentSaveSearch == null) {
            mCurrentSaveSearch = new CurrentSaveSearch();
        }
        return mCurrentSaveSearch;
    }

    public ArrayList<ListingFull> getArrListing() {
        return mArrListing;
    }

    public void setArrListing(ArrayList<ListingFull> mArrListing) {
        this.mArrListing = mArrListing;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public SearchDetail getSearchDetail() {
        return mSearchDetail;
    }

    public void setSearchDetail(SearchDetail mSearchDetail) {
        this.mSearchDetail = mSearchDetail;
    }

    public ArrayList<Participant> getArrParticipant() {
        return mArrParticipant;
    }

    public void setArrParticipant(ArrayList<Participant> mArrParticipant) {
        this.mArrParticipant = mArrParticipant;
    }
}
