package com.homecaravan.android.consumer.consumerteam.historymvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class HistoryHelper {

    private IHistoryHelper mHelper;

    public void setHelper(IHistoryHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getHistory() {
        mHelper.getSuccess(addData());
    }

    public ArrayList<ConsumerTeam> addData() {
        return ConsumerTeamData.getInstance().mData.getTeams();
    }
}
