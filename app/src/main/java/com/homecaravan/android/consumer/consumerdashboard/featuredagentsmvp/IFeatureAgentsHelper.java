package com.homecaravan.android.consumer.consumerdashboard.featuredagentsmvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

public interface IFeatureAgentsHelper {

    void getSuccess(ArrayList<ConsumerTeam> teams);

    void getError(String message);
}
