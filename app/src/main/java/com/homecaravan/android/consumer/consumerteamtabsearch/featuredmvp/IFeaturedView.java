package com.homecaravan.android.consumer.consumerteamtabsearch.featuredmvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public interface IFeaturedView {

    void showFeatured(ArrayList<ConsumerTeam> teams);

    void showError(String message);
}
