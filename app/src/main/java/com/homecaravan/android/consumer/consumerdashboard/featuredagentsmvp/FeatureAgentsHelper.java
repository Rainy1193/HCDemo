package com.homecaravan.android.consumer.consumerdashboard.featuredagentsmvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

public class FeatureAgentsHelper {
    private IFeatureAgentsHelper mHelper;

    public void setHelper(IFeatureAgentsHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getFeatureAgents() {
        mHelper.getSuccess(addData());
    }


    public ArrayList<ConsumerTeam> addData() {
        return ConsumerTeamData.getInstance().mData.getTeams();
    }
}
