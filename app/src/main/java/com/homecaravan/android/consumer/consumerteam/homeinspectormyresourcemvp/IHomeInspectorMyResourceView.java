package com.homecaravan.android.consumer.consumerteam.homeinspectormyresourcemvp;

import com.homecaravan.android.consumer.model.ConsumerTeam;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public interface IHomeInspectorMyResourceView {

    void showHomeInspectorMyResource(ArrayList<ConsumerTeam> teams);

    void showError(String message);
}
