package com.homecaravan.android.consumer.consumerteam.homeinspectorfeaturedmvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class HomeInspectorFeaturedPresenter implements IHomeInspectorFeaturedHelper {

    private HomeInspectorFeaturedHelper mHelper;
    private IHomeInspectorFeaturedView mView;

    public HomeInspectorFeaturedPresenter(HomeInspectorFeaturedHelper mHelper, IHomeInspectorFeaturedView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }

    @Override
    public void getSuccess(ArrayList<ConsumerTeam> teams) {
        mView.showHomeInspectorFeatured(teams);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);
    }

    public void getHomeInspectorFeatured() {
        mHelper.getHomeInspectorFeatured();
    }
}
