package com.homecaravan.android.consumer.consumermvp.loginmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.base.BaseView;
import com.homecaravan.android.consumer.model.responseapi.ResponseUser;

public interface LoginView extends BaseView {
    void loginSuccess(ResponseUser responseUser);

    void loginFail(String message);

    void serverError(@StringRes int message);

    void accountNotYetActivated(@StringRes int message, String emailOrPhone);

}
