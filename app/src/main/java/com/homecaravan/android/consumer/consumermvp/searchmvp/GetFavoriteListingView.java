package com.homecaravan.android.consumer.consumermvp.searchmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.Listing;

import java.util.ArrayList;

public interface GetFavoriteListingView {
    void getFavoriteSuccess(ArrayList<Listing> listings);

    void getFavoriteFail(String message);

    void getFavoriteFail(@StringRes int message);
}
