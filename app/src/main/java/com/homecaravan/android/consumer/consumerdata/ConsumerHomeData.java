package com.homecaravan.android.consumer.consumerdata;

import com.homecaravan.android.consumer.model.ResponseConsumerHome;

public class ConsumerHomeData {
    public static ConsumerHomeData mHomeData;
    public ResponseConsumerHome mData;

    public static ConsumerHomeData getInstance() {
        if (mHomeData == null) {
            mHomeData = new ConsumerHomeData();
        }
        return mHomeData;
    }


    public void setData(ResponseConsumerHome mData) {
        this.mData = mData;
    }
}
