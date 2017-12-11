package com.homecaravan.android.consumer.consumermvp.contactmvp;


import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ContactData;

public interface UpdateContactView {

    void updateContactSuccess(ContactData data);

    void updateContactFail(@StringRes int message);

    void updateContactFail(String message);
}
