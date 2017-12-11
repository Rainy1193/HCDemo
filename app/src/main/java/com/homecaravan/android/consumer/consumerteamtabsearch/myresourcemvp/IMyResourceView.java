package com.homecaravan.android.consumer.consumerteamtabsearch.myresourcemvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public interface IMyResourceView {

    void showMyResource(ArrayList<ConsumerTeam> teams);

    void showError(String message);
}
