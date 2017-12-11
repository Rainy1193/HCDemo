package com.homecaravan.android.consumer.consumermvp.listingmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.Listing;

public interface GetListingDetailView {

    void getListingDetailSuccess(Listing listing);

    void getListingDetailFail(String message);

    void getListingDetailFail(@StringRes int message);
}
