package com.homecaravan.android.consumer.consumerteam.homeinspectorfeaturedmvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public interface IHomeInspectorFeaturedHelper {

    void getSuccess(ArrayList<ConsumerTeam> teams);

    void getError(String message);
}
