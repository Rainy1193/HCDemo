package com.homecaravan.android.consumer.consumerdashboard.featuredagentsmvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

public interface FeatureAgentsView {

    void showFeatureAgents(ArrayList<ConsumerTeam> teams);

    void showError(String message);
}
