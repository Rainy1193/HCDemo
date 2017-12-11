package com.homecaravan.android.consumer.consumermvp.listingmvp;

import com.homecaravan.android.consumer.model.responseapi.ListingFull;

import java.util.ArrayList;

public interface GetJustListView {

    void getJustListSuccess(ArrayList<ListingFull> listingFulls);

    void getJustListFail();
}
