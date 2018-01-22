package com.homecaravan.android.consumer.consumermvp.searchmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.PolygonSearch;

public interface SavePolygonView {
    void savePolygonSuccess(PolygonSearch.PolygonSearchData data);
    void savePolygonFail(String message);
    void savePolygonFail(@StringRes int message);
}
