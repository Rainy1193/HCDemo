package com.homecaravan.android.consumer.consumerteamtabsearch.featuredmvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class FeaturedPresenter implements IFeaturedHelper {

    private FeaturedHelper mHelper;
    private IFeaturedView mView;

    public FeaturedPresenter(FeaturedHelper mHelper, IFeaturedView mView) {
        this.mHelper = mHelper;
        this.mView = mView;
        mHelper.setHelper(this);
    }

    @Override
    public void getSuccess(ArrayList<ConsumerTeam> teams) {
        mView.showFeatured(teams);
    }

    @Override
    public void getError(String message) {
        mView.showError(message);
    }

    public void getFeatured() {
        mHelper.getFeatured();
    }
}
