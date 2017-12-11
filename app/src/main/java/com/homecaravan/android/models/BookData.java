package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 5/5/2016.
 */
public class BookData {
    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("TIMESLOT_TYPE")
    @Expose
    private String timeSlotType;
    @SerializedName("STATUS")
    @Expose
    private String status;
    @SerializedName("SA_APPROVAL_REQUIRED")
    @Expose
    private String saAppovalRequired;
    @SerializedName("ADMIN_APPROVAL_REQUIRED")
    @Expose
    private String adminAppovalRequired;
    @SerializedName("SA_ACTION")
    @Expose
    private String saAction;
    @SerializedName("ADMIN_ACTION")
    @Expose
    private String adminAction;
    @SerializedName("USER_ACTION")
    @Expose
    private String userAction;
    @SerializedName("INACTIVE_STATUS")
    @Expose
    private String inactiveStatus;
    @SerializedName("TIME_FROM")
    @Expose
    private String timeFrom;
    @SerializedName("TIME_TO")
    @Expose
    private String timeTo;
    @SerializedName("APPT_NOTE")
    @Expose
    private String apptNote;
    @SerializedName("WITHOUT_AGENT")
    @Expose
    private String withoutAgent;
    @SerializedName("COMMUNICATION_TYPE")
    @Expose
    private String communticationType;
    @SerializedName("APPOINTMENT_TYPE")
    @Expose
    private String appointmentType;
    @SerializedName("SUPER_ADMIN_APPROVE")
    @Expose
    private String superAdminApprove;
    @SerializedName("SEND_FEEDBACK")
    @Expose
    private String sendFeedback;
    @SerializedName("SEND_REMINDER")
    @Expose
    private String sendReminder;
    @SerializedName("REPEAT_TYPE")
    @Expose
    private String repeatType;
    @SerializedName("STATUS_REASON")
    @Expose
    private String statusReason;
    @SerializedName("AUTHENTICATION_KEY")
    @Expose
    private String authenticationKey;
    @SerializedName("CALENDAR_UID")
    @Expose
    private String calendarUid;
    @SerializedName("CALENDAR_UPDATE_SEQUENCE")
    @Expose
    private String calendarUpdateSequence;
    @SerializedName("CREATED_DATETIME")
    @Expose
    private String createDateTime;
    @SerializedName("CREATED_BY")
    @Expose
    private String createdBy;
    @SerializedName("MODIFIED_DATETIME")
    @Expose
    private String modifiedDateTime;
    @SerializedName("MODIFIED_BY")
    @Expose
    private String modifiedBy;
    @SerializedName("LISTING_ID")
    @Expose
    private String listingId;
    @SerializedName("USER_ID")
    @Expose
    private String userId;
    @SerializedName("CLIENT_ID")
    @Expose
    private String clientId;
    @SerializedName("CONTENT_SHARE_EMAIL")
    @Expose
    private String contentShareEmail;
    @SerializedName("CONTENT_SHARE_SMS")
    @Expose
    private String contentShareSms;


    public String getAdminAction() {
        return adminAction;
    }

    public String getAdminAppovalRequired() {
        return adminAppovalRequired;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public String getApptNote() {
        return apptNote;
    }

    public String getAuthenticationKey() {
        return authenticationKey;
    }

    public String getCalendarUid() {
        return calendarUid;
    }

    public String getCalendarUpdateSequence() {
        return calendarUpdateSequence;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCommunticationType() {
        return communticationType;
    }

    public String getContentShareEmail() {
        return contentShareEmail;
    }

    public String getContentShareSms() {
        return contentShareSms;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getId() {
        return id;
    }

    public String getInactiveStatus() {
        return inactiveStatus;
    }

    public String getListingId() {
        return listingId;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public String getModifiedDateTime() {
        return modifiedDateTime;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public String getSaAction() {
        return saAction;
    }

    public String getSaAppovalRequired() {
        return saAppovalRequired;
    }

    public String getSendFeedback() {
        return sendFeedback;
    }

    public String getSendReminder() {
        return sendReminder;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public String getSuperAdminApprove() {
        return superAdminApprove;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeSlotType() {
        return timeSlotType;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public String getUserAction() {
        return userAction;
    }

    public String getUserId() {
        return userId;
    }

    public String getWithoutAgent() {
        return withoutAgent;
    }
}
