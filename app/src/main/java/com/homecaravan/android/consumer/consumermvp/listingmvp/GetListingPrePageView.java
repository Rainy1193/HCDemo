package com.homecaravan.android.consumer.consumermvp.listingmvp;

import com.homecaravan.android.consumer.model.responseapi.ListingFull;

import java.util.ArrayList;

public interface GetListingPrePageView {
    void getListingPrePageSuccess(ArrayList<ListingFull> listingFulls);
    void getListingPrePageFail();
}
