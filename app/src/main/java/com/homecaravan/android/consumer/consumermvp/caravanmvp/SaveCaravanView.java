package com.homecaravan.android.consumer.consumermvp.caravanmvp;


import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ResponseSaveCaravan;

public interface SaveCaravanView {
    void saveCaravanSuccess(ResponseSaveCaravan caravan);

    void saveCaravanFail(@StringRes int message);

    void saveCaravanFail(String message);
}
