package com.homecaravan.android.consumer.consumermvp.listingmvp;


import com.homecaravan.android.consumer.model.responseapi.ListingFull;

import java.util.ArrayList;

public interface GetTrendingView {
    void getTrendingSuccess(ArrayList<ListingFull> listingFulls);

    void getTrendingFail();
}
