package com.homecaravan.android.consumer.consumerdata;


import com.homecaravan.android.consumer.model.ConsumerListingFull;
import com.homecaravan.android.consumer.model.ResponseConsumerListingFull;

import java.util.ArrayList;

public class ConsumerListingFullData {
    public static ConsumerListingFullData mListingData;
    public ResponseConsumerListingFull mData;

    public static ConsumerListingFullData getInstance() {
        if (mListingData == null) {
            mListingData = new ConsumerListingFullData();
        }
        return mListingData;
    }

    public void setData(ResponseConsumerListingFull mData) {
        setDataSavedSearch(mData.getData().getListings());
        this.mData = mData;
    }

    public void setDataSavedSearch(ArrayList<ConsumerListingFull> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            ConsumerListingFullStatus listing = new ConsumerListingFullStatus();
            listing.setListing(arrayList.get(i));
            ConsumerSingletonListing.getInstance().arrListing.add(listing);
        }
    }
}
