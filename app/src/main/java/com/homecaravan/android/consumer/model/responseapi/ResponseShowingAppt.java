package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseShowingAppt extends BaseResponse {
    @Expose
    @SerializedName("data")
    ArrayList<AppointmentShowing> appointmentShowings;

    public ArrayList<AppointmentShowing> getAppointmentShowings() {
        return appointmentShowings;
    }
}
