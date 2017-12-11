package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;

public interface CreateCaravanView {

    void createCaravanSuccess(ResponseCaravan caravan);

    void createCaravanFail(String message);

    void createCaravanFail(@StringRes int message);

}
