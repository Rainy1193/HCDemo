package com.homecaravan.android.consumer.model;

import java.util.ArrayList;

public class ConsumerListingSavedSearch {
    public static ConsumerListingSavedSearch mListing;
    public ArrayList<ConsumerListingDiscover> mData;


    public static ConsumerListingSavedSearch getInstance() {
        if (mListing == null) {
            mListing = new ConsumerListingSavedSearch();
        }
        return mListing;
    }

    public void setData(ArrayList<ConsumerListingDiscover> mData) {
        this.mData = mData;
    }

}
