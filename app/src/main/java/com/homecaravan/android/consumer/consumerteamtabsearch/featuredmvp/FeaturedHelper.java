package com.homecaravan.android.consumer.consumerteamtabsearch.featuredmvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class FeaturedHelper {

    private IFeaturedHelper mHelper;

    public void setHelper(IFeaturedHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getFeatured() {
        mHelper.getSuccess(addData());
    }

    public ArrayList<ConsumerTeam> addData() {
        return ConsumerTeamData.getInstance().mData.getTeams();
    }
}
