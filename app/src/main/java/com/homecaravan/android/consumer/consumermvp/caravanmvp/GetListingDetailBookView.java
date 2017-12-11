package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.models.ListingDetailData;

public interface GetListingDetailBookView {
    void getListingDetailSuccess(ListingDetailData listingDetailData);

    void getListingDetailFail(@StringRes int message);

    void getListingDetailFail(String message);
}
