package com.homecaravan.android.consumer.consumermvp.searchmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.SearchAddress;

import org.json.JSONObject;

public interface SearchAddressView {
    void searchAddressSuccess(JSONObject searchAddress);

    void searchAddressFail(String message);

    void searchAddressFail(@StringRes int message);
}
