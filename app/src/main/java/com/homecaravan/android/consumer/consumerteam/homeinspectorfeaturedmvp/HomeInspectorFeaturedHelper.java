package com.homecaravan.android.consumer.consumerteam.homeinspectorfeaturedmvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class HomeInspectorFeaturedHelper {

    private IHomeInspectorFeaturedHelper mHelper;

    public void setHelper(IHomeInspectorFeaturedHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getHomeInspectorFeatured() {
        mHelper.getSuccess(addData());
    }

    public ArrayList<ConsumerTeam> addData() {
        return ConsumerTeamData.getInstance().mData.getTeams();
    }
}
