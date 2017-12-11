package com.homecaravan.android.consumer.consumerdata;

import com.homecaravan.android.consumer.model.ResponseConsumerListing;

public class ConsumerListingData {
    public static ConsumerListingData mListingsData;
    public ResponseConsumerListing mData;


    public static ConsumerListingData getInstance() {
        if (mListingsData == null) {
            mListingsData = new ConsumerListingData();
        }
        return mListingsData;
    }

    public void setData(ResponseConsumerListing mData) {
        this.mData = mData;
    }
}
