package com.homecaravan.android.consumer.consumerteam.lenderfeaturedmvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class LenderFeaturedHelper {

    private ILenderFeaturedHelper mHelper;

    public void setHelper(ILenderFeaturedHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getLenderFeatured() {
        mHelper.getSuccess(addData());
    }

    public ArrayList<ConsumerTeam> addData() {
        return ConsumerTeamData.getInstance().mData.getTeams();
    }
}
