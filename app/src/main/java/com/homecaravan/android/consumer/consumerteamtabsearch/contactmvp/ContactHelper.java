package com.homecaravan.android.consumer.consumerteamtabsearch.contactmvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerTeamData;
import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class ContactHelper {

    private IContactHelper mHelper;

    public void setHelper(IContactHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getContact() {
        mHelper.getSuccess(addData());
    }

    public ArrayList<ConsumerTeam> addData() {
        return ConsumerTeamData.getInstance().mData.getTeams();
    }
}
