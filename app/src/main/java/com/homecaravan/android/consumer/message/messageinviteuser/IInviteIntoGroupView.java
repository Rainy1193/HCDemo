package com.homecaravan.android.consumer.message.messageinviteuser;

import android.support.annotation.StringRes;

/**
 * Created by Anh Dao on 11/29/2017.
 */

public interface IInviteIntoGroupView {

    void invitedSuccess();
    void invitedFail();
    void serverError(@StringRes int message);
}
