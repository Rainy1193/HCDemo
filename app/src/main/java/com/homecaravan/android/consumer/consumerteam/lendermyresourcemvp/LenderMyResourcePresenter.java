package com.homecaravan.android.consumer.consumerteam.lendermyresourcemvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class LenderMyResourcePresenter implements ILenderMyResourceHelper {

    private LenderMyResourceHelper mHelper;
    private ILenderMyResourceView mView;

    public LenderMyResourcePresenter(LenderMyResourceHelper mHelper, ILenderMyResourceView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }

    @Override
    public void getSuccess(ArrayList<ConsumerTeam> teams) {
        mView.showLenderMyResource(teams);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);
    }

    public void getLenderMyResource() {
        mHelper.getLenderMyResource();
    }
}
