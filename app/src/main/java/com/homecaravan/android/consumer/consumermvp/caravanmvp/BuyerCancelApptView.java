package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.AppointmentShowing;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 10/23/2017.
 */

public interface BuyerCancelApptView {

    void cancelApptSuccess(ArrayList<AppointmentShowing> appointmentShowing);

    void cancelApptFail();

    void cancelApptFail(@StringRes int message);
}
