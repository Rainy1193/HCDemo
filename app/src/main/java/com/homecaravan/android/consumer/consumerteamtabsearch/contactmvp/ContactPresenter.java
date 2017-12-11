package com.homecaravan.android.consumer.consumerteamtabsearch.contactmvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class ContactPresenter implements IContactHelper {

    private ContactHelper mHelper;
    private IContactView mView;

    public ContactPresenter(ContactHelper mHelper, IContactView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }

    @Override
    public void getSuccess(ArrayList<ConsumerTeam> teams) {
        mView.showContact(teams);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);
    }

    public void getContact() {
        mHelper.getContact();
    }
}
