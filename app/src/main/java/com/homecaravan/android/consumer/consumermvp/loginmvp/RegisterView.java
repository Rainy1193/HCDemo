package com.homecaravan.android.consumer.consumermvp.loginmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ResponseRegister;


public interface RegisterView {

    void registerSuccess(@StringRes int message, ResponseRegister responseRegister);

    void registerFail(String message);

    void serverError(@StringRes int message);

}
