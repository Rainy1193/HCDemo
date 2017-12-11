package com.homecaravan.android.consumer.consumerdashboard.featuredagentsmvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

public class FeatureAgentsPresenter implements IFeatureAgentsHelper {
    private FeatureAgentsHelper mHelper;
    private FeatureAgentsView mView;

    public FeatureAgentsPresenter(FeatureAgentsHelper mHelper, FeatureAgentsView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }


    @Override
    public void getSuccess(ArrayList<ConsumerTeam> teams) {
        mView.showFeatureAgents(teams);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);
    }

    public void getFeatureAgents() {
        mHelper.getFeatureAgents();
    }
}
