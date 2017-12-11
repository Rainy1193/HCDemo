package com.homecaravan.android.consumer.consumermvp.queuemvp;

import com.homecaravan.android.consumer.model.responseapi.Listing;

public interface AddQueueView {
    void addQueueSuccess(Listing listing);

    void addQueueFail();
}
