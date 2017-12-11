package com.homecaravan.android.consumer.consumermvp.loginmvp;


import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ResponseUser;

public interface RegisterFacebookView {

    void registerFacebookSuccess(ResponseUser responseUser);

    void registerFacebookFail(String message);

    void serverError(@StringRes int message);
}
