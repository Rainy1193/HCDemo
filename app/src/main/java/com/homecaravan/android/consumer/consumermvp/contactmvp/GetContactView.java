package com.homecaravan.android.consumer.consumermvp.contactmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ContactData;

public interface GetContactView {
    void getContactSuccess(ContactData data);

    void getContactFail(@StringRes int message);

    void getContactFail(String message);
}
