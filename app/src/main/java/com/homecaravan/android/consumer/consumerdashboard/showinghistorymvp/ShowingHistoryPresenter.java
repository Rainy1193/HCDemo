package com.homecaravan.android.consumer.consumerdashboard.showinghistorymvp;


import com.homecaravan.android.consumer.consumerdata.ConsumerListingFullStatus;

import java.util.ArrayList;

public class ShowingHistoryPresenter implements IShowingHistoryHelper {
    private ShowingHistoryHelper mHelper;
    private ShowingHistoryView mView;

    public ShowingHistoryPresenter(ShowingHistoryHelper mHelper, ShowingHistoryView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }


    @Override
    public void getSuccess(ArrayList<ConsumerListingFullStatus> showing) {
        mView.showShowingHistory(showing);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);

    }

    public void getDataShowingHistory() {
        mHelper.getShowingHistory();
    }
}
