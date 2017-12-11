package com.homecaravan.android.consumer.consumerteam.realtorfeaturedmvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class RealtorFeaturedPresenter implements IRealtorFeaturedHelper {

    private RealtorFeaturedHelper mHelper;
    private IRealtorFeaturedView mView;

    public RealtorFeaturedPresenter(RealtorFeaturedHelper mHelper, IRealtorFeaturedView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }

    @Override
    public void getSuccess(ArrayList<ConsumerTeam> teams) {
        mView.showRealtorFeatured(teams);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);
    }

    public void getRealtorFeatured() {
        mHelper.getRealtorFeatured();
    }
}
