package com.homecaravan.android.consumer.consumerteam.homeinspectormyresourcemvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class HomeInspectorMyResourceHelper {

    private IHomeInspectorMyResourceHelper mHelper;

    public void setHelper(IHomeInspectorMyResourceHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getHomeInspectorMyResource() {
        mHelper.getSuccess(addData());
    }

    public ArrayList<ConsumerTeam> addData() {
        return ConsumerTeamData.getInstance().mData.getTeams();
    }
}
