package com.homecaravan.android.consumer.model.listitem;

import com.homecaravan.android.consumer.model.BaseDataRecyclerView;
import com.homecaravan.android.consumer.model.responseapi.ListingFull;

public class ListingDashboard extends BaseDataRecyclerView {
    private ListingFull listingFull;

    public ListingFull getListingFull() {
        return listingFull;
    }

    public void setListingFull(ListingFull listingFull) {
        this.listingFull = listingFull;
    }
}
