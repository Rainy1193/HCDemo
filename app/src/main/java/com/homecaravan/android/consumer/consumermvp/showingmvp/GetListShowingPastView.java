package com.homecaravan.android.consumer.consumermvp.showingmvp;


import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.CaravanShowing;

import java.util.ArrayList;

public interface GetListShowingPastView {

    void getShowingCaravanSuccess(ArrayList<CaravanShowing> caravans);

    void getShowingCaravanFail(String message);

    void getShowingCaravanFail(@StringRes int message);
}
