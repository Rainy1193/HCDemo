package com.homecaravan.android.consumer.consumermvp.searchmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.SearchDetail;

public interface UpdateParticipantView {
    void updateParticipantSuccess(SearchDetail searchDetail);

    void updateParticipantFail(String message);

    void updateParticipantFail(@StringRes int message);

}
