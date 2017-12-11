package com.homecaravan.android.consumer.consumermvp.loginmvp;

import android.support.annotation.StringRes;

public interface SetAgentView {
    void setAgentSuccess();
    void setAgentFail(String message);
    void setAgentFail(@StringRes int message);
}
