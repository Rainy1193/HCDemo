package com.homecaravan.android.consumer.consumerdashboard.showinghistorymvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerListingFullStatus;

import java.util.ArrayList;

public interface IShowingHistoryHelper {


    void getSuccess(ArrayList<ConsumerListingFullStatus> showing);

    void getError(String message);
}
