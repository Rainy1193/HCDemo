package com.homecaravan.android.consumer.consumermvp.contactmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ContactData;

public interface CreateContactView {
    void createContactSuccess(ContactData data);

    void createContactFail(@StringRes int message);

    void createContactFail(String message);
}
