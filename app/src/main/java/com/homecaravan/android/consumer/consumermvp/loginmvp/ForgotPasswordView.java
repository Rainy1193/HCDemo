package com.homecaravan.android.consumer.consumermvp.loginmvp;

import android.support.annotation.StringRes;

/**
 * Created by Anh Dao on 11/13/2017.
 */

public interface ForgotPasswordView {
    void forgotPasswordSuccess(String message, String emailOrPhone);
    void forgotPasswordFail(String message);
    void serverError(@StringRes int message);
}
