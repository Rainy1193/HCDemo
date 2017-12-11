package com.homecaravan.android.consumer.consumerdata;


import com.homecaravan.android.consumer.model.ResponseConsumerTeam;

public class ConsumerTeamData {
    public static ConsumerTeamData mTeamData;
    public ResponseConsumerTeam mData;


    public static ConsumerTeamData getInstance() {
        if (mTeamData == null) {
            mTeamData = new ConsumerTeamData();
        }
        return mTeamData;
    }

    public void setData(ResponseConsumerTeam mData) {
        this.mData = mData;
    }
}
