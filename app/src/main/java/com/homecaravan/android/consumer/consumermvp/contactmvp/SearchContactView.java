package com.homecaravan.android.consumer.consumermvp.contactmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ContactData;

import java.util.ArrayList;

public interface SearchContactView {

    void searchContactSuccess(ArrayList<ContactData> data);

    void searchContactFail(@StringRes int message);

    void searchContactFail(String message);
}
