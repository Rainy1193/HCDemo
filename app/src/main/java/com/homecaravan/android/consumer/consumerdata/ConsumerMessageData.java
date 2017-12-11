package com.homecaravan.android.consumer.consumerdata;


import com.homecaravan.android.consumer.model.ResponseConsumerMessage;

public class ConsumerMessageData {
    public static ConsumerMessageData mMessageData;
    public ResponseConsumerMessage mData;


    public static ConsumerMessageData getInstance() {
        if (mMessageData == null) {
            mMessageData = new ConsumerMessageData();
        }
        return mMessageData;
    }

    public void setData(ResponseConsumerMessage mData) {
        this.mData = mData;
    }
}
