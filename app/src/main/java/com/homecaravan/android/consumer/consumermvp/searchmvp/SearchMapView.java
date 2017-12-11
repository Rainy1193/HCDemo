package com.homecaravan.android.consumer.consumermvp.searchmvp;


import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ResponseSearchMap;

public interface SearchMapView {
    void searchMapSuccess(ResponseSearchMap responseSearchMap);

    void searchMapFail(String message);

    void searchMapFail(@StringRes int message);
}
