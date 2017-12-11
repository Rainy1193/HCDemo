package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vankhoadesign on 9/28/16.
 */

public class SuggestionEvent {
    @SerializedName("TIME_FROM")
    @Expose
    private String timeFrom;
    @SerializedName("TIME_TO")
    @Expose
    private String timeTo;

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrome) {
        this.timeFrom = timeFrome;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    @Override
    public String toString() {
        return "SuggestionEvent{" +
                "timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                '}';
    }
}
