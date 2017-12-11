package com.homecaravan.android.consumer.consumermvp.searchmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.SearchDetail;

public interface RemoveParticipantSearchView {
    void removeParticipantSuccess(SearchDetail searchDetail);

    void removeParticipantFail(String message);

    void removeParticipantFail(@StringRes int message);
}
