package com.homecaravan.android.consumer.consumermvp.accountsettingmvp;

import android.support.annotation.StringRes;

/**
 * Created by Anh Dao on 11/8/2017.
 */

public interface IAccountSettingView {
    void changePasswordSuccess(String token);
    void changePasswordFail(String message);
    void changePasswordFail(@StringRes int message);
}
