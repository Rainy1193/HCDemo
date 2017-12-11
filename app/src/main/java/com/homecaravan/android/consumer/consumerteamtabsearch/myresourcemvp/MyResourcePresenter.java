package com.homecaravan.android.consumer.consumerteamtabsearch.myresourcemvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class MyResourcePresenter implements IMyResourceHelper {

    private MyResourceHelper mHelper;
    private IMyResourceView mView;

    public MyResourcePresenter(MyResourceHelper mHelper, IMyResourceView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }

    @Override
    public void getSuccess(ArrayList<ConsumerTeam> teams) {
        mView.showMyResource(teams);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);
    }

    public void getMyResource() {
        mHelper.getMyResource();
    }
}
