package com.homecaravan.android.consumer.consumermvp.searchmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.SearchDetail;

public interface AddParticipantSearchView {
    void addParticipantSuccess(SearchDetail searchDetail);

    void addParticipantFail(String message);

    void addParticipantFail(@StringRes int message);
}
