package com.homecaravan.android.consumer.consumermvp.searchmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.SearchDetail;

public interface SaveSearchView {
    void saveSearchSuccess(SearchDetail searchDetail);

    void saveSearchFail(String message);

    void saveSearchFail(@StringRes int message);
}
