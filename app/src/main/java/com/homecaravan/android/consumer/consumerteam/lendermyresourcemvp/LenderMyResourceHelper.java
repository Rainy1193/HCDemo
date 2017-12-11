package com.homecaravan.android.consumer.consumerteam.lendermyresourcemvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class LenderMyResourceHelper {

    private ILenderMyResourceHelper mHelper;

    public void setHelper(ILenderMyResourceHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getLenderMyResource() {
        mHelper.getSuccess(addData());
    }

    public ArrayList<ConsumerTeam> addData() {
        return ConsumerTeamData.getInstance().mData.getTeams();
    }
}
