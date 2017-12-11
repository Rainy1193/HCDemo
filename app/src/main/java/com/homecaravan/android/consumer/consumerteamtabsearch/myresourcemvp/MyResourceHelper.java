package com.homecaravan.android.consumer.consumerteamtabsearch.myresourcemvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class MyResourceHelper {

    private IMyResourceHelper mHelper;

    public void setHelper(IMyResourceHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getMyResource() {
        mHelper.getSuccess(addData());
    }

    public ArrayList<ConsumerTeam> addData() {
        return ConsumerTeamData.getInstance().mData.getTeams();
    }
}
