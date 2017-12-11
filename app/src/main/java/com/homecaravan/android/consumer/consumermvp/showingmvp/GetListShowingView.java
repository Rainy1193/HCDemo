package com.homecaravan.android.consumer.consumermvp.showingmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.AppointmentShowing;
import com.homecaravan.android.consumer.model.responseapi.CaravanShowing;

import java.util.ArrayList;

public interface GetListShowingView {

    void getShowingApptSuccess(ArrayList<AppointmentShowing> appointments);

    void getShowingCaravanSuccess(ArrayList<CaravanShowing> caravans);

    void getShowingApptFail(String message);

    void getShowingCaravanFail(String message);

    void getShowingApptFail(@StringRes int message);

    void getShowingCaravanFail(@StringRes int message);
}
