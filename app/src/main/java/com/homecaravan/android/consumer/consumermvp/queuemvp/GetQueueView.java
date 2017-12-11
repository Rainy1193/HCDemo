package com.homecaravan.android.consumer.consumermvp.queuemvp;

import com.homecaravan.android.consumer.model.responseapi.ResponseQueue;

import java.util.ArrayList;

public interface GetQueueView {
    void getQueueSuccess(ArrayList<ResponseQueue.QueueData> listing);

    void getQueueFail();
}
