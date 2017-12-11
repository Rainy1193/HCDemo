package com.homecaravan.android.consumer.consumermvp.loginmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.User;

/**
 * Created by Anh Dao on 11/9/2017.
 */

public interface VerifyAccountView {
    void verifySuccess(User data);
    void verifyFail(String message);
    void accountActivated(@StringRes int message, String emailOrPhone);
    void resendCodeSuccess(String message);
    void resendCodeFail(String message);
    void serverError(@StringRes int message);
}
