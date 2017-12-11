package com.homecaravan.android.consumer.consumermvp.listingmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ResponseAddListing;

public interface CreateListingView {
    void createListingSuccess(ResponseAddListing.DataAddListing dataAddListing);

    void createListingFail(@StringRes int message);

    void createListingFail(String message);
}
