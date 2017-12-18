package com.homecaravan.android.consumer.consumermvp.contactmvp;

import android.support.annotation.StringRes;

public interface InviteContactView {
    void inviteSuccess();

    void inviteFail(String message);

    void inviteFail(@StringRes int message);
}
