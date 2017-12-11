package com.homecaravan.android.consumer.consumerteam.homeinspectormyresourcemvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class HomeInspectorMyResourcePresenter implements IHomeInspectorMyResourceHelper {

    private HomeInspectorMyResourceHelper mHelper;
    private IHomeInspectorMyResourceView mView;

    public HomeInspectorMyResourcePresenter(HomeInspectorMyResourceHelper mHelper, IHomeInspectorMyResourceView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }

    @Override
    public void getSuccess(ArrayList<ConsumerTeam> teams) {
        mView.showHomeInspectorMyResource(teams);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);
    }

    public void getHomeInspectorMyResource() {
        mHelper.getHomeInspectorMyResource();
    }
}
