package com.homecaravan.android.consumer.consumerteam.lenderfeaturedmvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class LenderFeaturedPresenter implements ILenderFeaturedHelper {

    private LenderFeaturedHelper mHelper;
    private ILenderFeaturedView mView;

    public LenderFeaturedPresenter(LenderFeaturedHelper mHelper, ILenderFeaturedView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }

    @Override
    public void getSuccess(ArrayList<ConsumerTeam> teams) {
        mView.showLenderFeatured(teams);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);
    }

    public void getLenderFeatured() {
        mHelper.getLenderFeatured();
    }
}
