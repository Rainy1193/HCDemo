package com.homecaravan.android.consumer.consumermvp.contactmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ContactData;

import java.util.ArrayList;

public interface GetListContactView {

    void getListContactSuccess(ArrayList<ContactData> data);

    void getListContactFail(@StringRes int message);

    void getListContactFail(String message);
}
