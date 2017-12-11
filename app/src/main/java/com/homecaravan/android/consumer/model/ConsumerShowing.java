package com.homecaravan.android.consumer.model;

/**
 * Created by Anh Dao on 8/8/2017.
 */

public class ConsumerShowing {
    private ConsumerUpcoming upcoming;
    private boolean isExpand;

    public ConsumerUpcoming getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(ConsumerUpcoming upcoming) {
        this.upcoming = upcoming;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}
