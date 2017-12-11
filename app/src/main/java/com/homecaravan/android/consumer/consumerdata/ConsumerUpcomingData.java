package com.homecaravan.android.consumer.consumerdata;


import com.homecaravan.android.consumer.model.ResponseConsumerUpcoming;

public class ConsumerUpcomingData {
    public static ConsumerUpcomingData mUpcomingData;
    public ResponseConsumerUpcoming mData;


    public static ConsumerUpcomingData getInstance() {
        if (mUpcomingData == null) {
            mUpcomingData = new ConsumerUpcomingData();
        }
        return mUpcomingData;
    }

    public void setData(ResponseConsumerUpcoming mData) {
        this.mData = mData;
    }
}
