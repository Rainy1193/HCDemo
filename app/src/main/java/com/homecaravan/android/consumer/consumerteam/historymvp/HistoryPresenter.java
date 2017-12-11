package com.homecaravan.android.consumer.consumerteam.historymvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class HistoryPresenter implements IHistoryHelper {

    private HistoryHelper mHelper;
    private IHistoryView mView;

    public HistoryPresenter(HistoryHelper mHelper, IHistoryView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }

    @Override
    public void getSuccess(ArrayList<ConsumerTeam> teams) {
        mView.showHistory(teams);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);
    }

    public void getHistory() {
        mHelper.getHistory();
    }
}
