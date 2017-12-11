package com.homecaravan.android.consumer.consumerteam.realtorfeaturedmvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class RealtorFeaturedHelper {

    private IRealtorFeaturedHelper mHelper;

    public void setHelper(IRealtorFeaturedHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getRealtorFeatured() {
        mHelper.getSuccess(addData());
    }

    public ArrayList<ConsumerTeam> addData() {
        return ConsumerTeamData.getInstance().mData.getTeams();
    }
}
