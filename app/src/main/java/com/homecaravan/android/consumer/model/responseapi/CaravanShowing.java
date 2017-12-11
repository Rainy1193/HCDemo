package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaravanShowing {

    private boolean expand;
    private boolean past;

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("start")
    private String start;
    @Expose
    @SerializedName("end")
    private String end;
    @Expose
    @SerializedName("backgroundColor")
    private String backgroundColor;
    @Expose
    @SerializedName("borderColor")
    private String borderColor;
    @Expose
    @SerializedName("eventType")
    private String eventType;
    @Expose
    @SerializedName("ref")
    private ResponseCaravan.CaravanData refCaravan;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public String getEventType() {
        return eventType;
    }

    public ResponseCaravan.CaravanData getRefCaravan() {
        return refCaravan;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isPast() {
        return past;
    }

    public void setPast(boolean past) {
        this.past = past;
    }
}
