package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 7/8/2016.
 */
public class TimeSlot {
    @SerializedName("DATETIME_FROM")
    @Expose
    private String dateTimeFrom;
    @SerializedName("DATETIME_TO")
    @Expose
    private String dateTimeTo;
    @SerializedName("STATUS")
    @Expose
    private String status;

    @SerializedName("APPOINTMENT_EXIST")
    @Expose
    private String apptExit;
    @SerializedName("BOOKING_EXIST")
    @Expose
    private String bookExit;
    @SerializedName("APPOINTMENT_IDS")
    @Expose
    private String apptId;

    public String getDateTimeFrom() {
        return dateTimeFrom;
    }

    public String getDateTimeTo() {
        return dateTimeTo;
    }

    public String getStatus() {
        return status;
    }

    public String getApptExit() {
        return apptExit;
    }

    public String getBookExit() {
        return bookExit;
    }

    public String getApptId() {
        return apptId;
    }
}
