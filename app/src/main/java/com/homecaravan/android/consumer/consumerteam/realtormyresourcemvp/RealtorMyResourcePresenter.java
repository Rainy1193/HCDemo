package com.homecaravan.android.consumer.consumerteam.realtormyresourcemvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class RealtorMyResourcePresenter implements IRealtorMyResourceHelper {

    private RealtorMyResourceHelper mHelper;
    private IRealtorMyResourceView mView;

    public RealtorMyResourcePresenter(RealtorMyResourceHelper mHelper, IRealtorMyResourceView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }

    @Override
    public void getSuccess(ArrayList<ConsumerTeam> teams) {
        mView.showRealtorMyResource(teams);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);
    }

    public void getRealtorMyResource() {
        mHelper.getRealtorMyResource();
    }
}
