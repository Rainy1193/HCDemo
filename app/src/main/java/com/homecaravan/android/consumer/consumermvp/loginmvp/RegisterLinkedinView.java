package com.homecaravan.android.consumer.consumermvp.loginmvp;


import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ResponseUser;

public interface RegisterLinkedinView {

    void registerLinkedinSuccess(ResponseUser responseUser);

    void registerLinkedinFail(String message);

    void serverError(@StringRes int message);
}
