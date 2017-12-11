package com.homecaravan.android.consumer.message.messagecontactmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ContactData;

import java.util.ArrayList;

public interface IMessageContactView {

    void getMessageContactSuccess(ArrayList<ContactData> messages);

    void getMessageContactFail(String message);

    void serverError(@StringRes int message);
}
