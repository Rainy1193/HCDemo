package com.homecaravan.android.consumer.message.messageinviteuser;

import android.support.annotation.StringRes;

/**
 * Created by Anh Dao on 11/29/2017.
 */

public interface InviteIntoGroupView {

    void invitedSuccess();
    void invitedFail(String message);
    void serverError(@StringRes int message);
}
