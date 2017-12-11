package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 4/5/2016.
 */
public class Statistic {
    @SerializedName("NEW_NOTIFICATION")
    @Expose
    private String newNotification;
    @SerializedName("MESSAGES_NEW")
    @Expose
    private String newMessages;
    @SerializedName("MESSAGES_TOTAL")
    @Expose
    private String totalMessages;
    @SerializedName("MYSHOWING_APPROVED")
    @Expose
    private String approvedMyShowing;
    @SerializedName("MYSHOWING_UPCOMING")
    @Expose
    private String upCompingMyShowing;
    @SerializedName("MYLISTING_APPT_APPROVAL")
    @Expose
    private String approvalMyListing;
    @SerializedName("MYLISTING_APPT_APRROVED")
    @Expose
    private String approvedMyListing;

    @SerializedName("MYLISTING_APPT_TOTAL")
    @Expose
    private String totalApptMyListing;

    public String getNewNotification() {
        return newNotification;
    }

    public String getNewMessages() {
        return newMessages;
    }

    public String getTotalMessages() {
        return totalMessages;
    }

    public String getApprovedMyShowing() {
        return approvedMyShowing;
    }

    public String getUpCompingMyShowing() {
        return upCompingMyShowing;
    }

    public String getApprovalMyListing() {
        return approvalMyListing;
    }

    public String getApprovedMyListing() {
        return approvedMyListing;
    }

    public String getTotalApptMyListing() {
        return totalApptMyListing;
    }

    public void setTotalApptMyListing(String totalApptMyListing) {
        this.totalApptMyListing = totalApptMyListing;
    }
}
