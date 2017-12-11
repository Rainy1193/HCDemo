package com.homecaravan.android.consumer.consumerteam.realtormyresourcemvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class RealtorMyResourceHelper {

    private IRealtorMyResourceHelper mHelper;

    public void setHelper(IRealtorMyResourceHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getRealtorMyResource() {
        mHelper.getSuccess(addData());
    }

    public ArrayList<ConsumerTeam> addData() {
        return ConsumerTeamData.getInstance().mData.getTeams();
    }
}
