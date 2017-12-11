package com.homecaravan.android.consumer.unlockagent;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.User;

/**
 * Created by Anh Dao on 11/3/2017.
 */

public interface IUnlockApi {
    void findAgentSuccess(User data);
    void findAgentFail(String message);
    void serverError(@StringRes int message);
}
