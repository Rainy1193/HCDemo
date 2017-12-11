package com.homecaravan.android.consumer.consumerdashboard.showinghistorymvp;

import com.homecaravan.android.consumer.consumerdata.ConsumerListingFullStatus;

import java.util.ArrayList;

public interface ShowingHistoryView {

    void showShowingHistory(ArrayList<ConsumerListingFullStatus> showing);

    void showError(String message);
}
