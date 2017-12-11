package com.homecaravan.android.consumer.consumerdashboard.showinghistorymvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerListingFullStatus;
import com.homecaravan.android.consumer.consumerdata.ConsumerSingletonListing;

import java.util.ArrayList;

public class ShowingHistoryHelper {
    private IShowingHistoryHelper mHelper;

    public void setHelper(IShowingHistoryHelper mHelper) {
        this.mHelper = mHelper;
    }

    public void getShowingHistory() {
        mHelper.getSuccess(addData());
    }


    public ArrayList<ConsumerListingFullStatus> addData() {
        return ConsumerSingletonListing.getInstance().arrListing;
    }
}
