package com.homecaravan.android.consumer.consumerteam.realtorfeaturedmvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public interface IRealtorFeaturedView {

    void showRealtorFeatured(ArrayList<ConsumerTeam> teams);

    void showError(String message);
}
