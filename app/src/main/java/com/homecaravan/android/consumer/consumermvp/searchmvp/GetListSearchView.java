package com.homecaravan.android.consumer.consumermvp.searchmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ResponseAllSearch;


public interface GetListSearchView {
    void getListSearchSuccess(ResponseAllSearch.ResponseAllSearchData arrSearch);

    void getListSearchFail(String message);

    void getListSearchFail(@StringRes int message);
}
