package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import android.support.annotation.StringRes;

import org.json.JSONObject;

public interface BookSingleView {

    void bookSuccess(JSONObject bookData);
    void bookNotSucceed(String message);
    void bookNotSucceedStatus(String message);
    void bookFail(@StringRes int message);
    void bookFail(String message);
}
