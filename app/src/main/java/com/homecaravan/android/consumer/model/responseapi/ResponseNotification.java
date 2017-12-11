package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 11/7/2017.
 */

public class ResponseNotification extends BaseResponse {
    @Expose
    @SerializedName("data")
    private NotificationData notificationData;

    public NotificationData getNotificationData() {
        return notificationData;
    }

    public class NotificationData{
        @Expose
        @SerializedName("notification")
        private ArrayList<Notifications> notifications;

        @Expose
        @SerializedName("total")
        private int total;

        public ArrayList<Notifications> getNotifications() {
            return notifications;
        }

        public int getTotal() {
            return total;
        }
    }

    public class Notifications{
        @Expose
        @SerializedName("SEEN_STATUS")
        private String seenStatus;
        @Expose
        @SerializedName("FOLLOWUP_STATUS")
        private String followupStatus;
        @Expose
        @SerializedName("ACTION")
        private String action;
        @Expose
        @SerializedName("MESSAGE")
        private String message;
        @Expose
        @SerializedName("ACTION_NAME")
        private String actionName;
        @Expose
        @SerializedName("ACTION_TARGET")
        private String actionTarget;
        @Expose
        @SerializedName("ACTION_ID")
        private String actionId;
        @Expose
        @SerializedName("TYPE")
        private String type;
        @Expose
        @SerializedName("CREATED_DATEITME")
        private String createdDatetime;
        @Expose
        @SerializedName("APPT_ID")
        private String apptId;
        @Expose
        @SerializedName("LISTING_ID")
        private String listingId;
        @Expose
        @SerializedName("CBS_ID")
        private String cbsId;

        private String filterType;

        public String getSeenStatus() {
            return seenStatus;
        }

        public String getFollowupStatus() {
            return followupStatus;
        }

        public String getAction() {
            return action;
        }

        public String getMessage() {
            return message;
        }

        public String getActionName() {
            return actionName;
        }

        public String getActionTarget() {
            return actionTarget;
        }

        public String getActionId() {
            return actionId;
        }

        public String getType() {
            return type;
        }

        public String getCreatedDatetime() {
            return createdDatetime;
        }

        public String getApptId() {
            return apptId;
        }

        public String getListingId() {
            return listingId;
        }

        public String getCbsId() {
            return cbsId;
        }

        public String getFilterType() {
            return filterType;
        }

        public void setFilterType(String filterType) {
            this.filterType = filterType;
        }
    }
}
