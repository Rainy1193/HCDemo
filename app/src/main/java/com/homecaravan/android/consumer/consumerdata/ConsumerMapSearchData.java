package com.homecaravan.android.consumer.consumerdata;


import com.homecaravan.android.consumer.model.ConsumerListingDiscover;
import com.homecaravan.android.consumer.model.ConsumerListingSavedSearch;
import com.homecaravan.android.consumer.model.ConsumerMapSearch;
import com.homecaravan.android.consumer.model.ResponseConsumerMapSearch;

import java.util.ArrayList;

public class ConsumerMapSearchData {
    public static ConsumerMapSearchData mSearchData;
    public ResponseConsumerMapSearch mData;


    public static ConsumerMapSearchData getInstance() {
        if (mSearchData == null) {
            mSearchData = new ConsumerMapSearchData();
        }
        return mSearchData;
    }

    public void setData(ResponseConsumerMapSearch mData) {
        setDataSavedSearch(mData.getMapSearches());
        this.mData = mData;
    }

    private void setDataSavedSearch(ArrayList<ConsumerMapSearch> listings) {
        ArrayList<ConsumerListingDiscover> discovers = new ArrayList<>();
        for (int i = 0; i < listings.size(); i++) {
            ConsumerListingDiscover listingDiscover = new ConsumerListingDiscover();
            listingDiscover.setListing(listings.get(i));
            discovers.add(listingDiscover);
        }
        ConsumerListingSavedSearch.getInstance().setData(discovers);
    }
}
