package com.homecaravan.android.consumer.consumermvp.searchmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.SearchDetail;

public interface GetSearchDetailView {
    void getSearchDetailSuccess(SearchDetail searchDetail);

    void getSearchDetailFail(String message);

    void getSearchDetailFail(@StringRes int message);
}
