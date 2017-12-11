package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import android.support.annotation.StringRes;

import org.json.JSONObject;

public interface SubmitAstView {
    void suggestionsubmitSucceed(JSONObject bookData);

    void suggestionsubmitFailed(String message);

    void suggestionsubmitFailed(@StringRes int message);

    void suggestionsubmitCancelled();
}
