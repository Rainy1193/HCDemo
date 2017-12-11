package com.homecaravan.android.consumer.consumermvp.searchmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ListingFull;

public interface VoteListingView {
    void voteSuccess(ListingFull listingFull);

    void voteFail(String message);

    void voteFail(@StringRes int message);
}
