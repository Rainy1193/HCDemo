package com.homecaravan.android.consumer.consumermvp.contactmvp;

import android.support.annotation.StringRes;

public interface DeleteContactView {
    void deleteContactSuccess(@StringRes int message);

    void deleteContactFail(@StringRes int message);

    void deleteContactFail(String message);
}
