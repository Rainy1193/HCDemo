package com.homecaravan.android.consumer.consumerdata;


import com.homecaravan.android.consumer.model.ResponseConsumerAgent;

public class ConsumerAgentData {
    public static ConsumerAgentData mAgentData;
    public ResponseConsumerAgent mData;


    public static ConsumerAgentData getInstance() {
        if (mAgentData == null) {
            mAgentData = new ConsumerAgentData();
        }
        return mAgentData;
    }

    public void setData(ResponseConsumerAgent mData) {
        this.mData = mData;
    }
}
