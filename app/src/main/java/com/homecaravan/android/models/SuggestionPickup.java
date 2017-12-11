package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vankhoadesign on 8/17/16.
 */
public class SuggestionPickup {
    @SerializedName("ID")
    @Expose
    private String suggestionId;

    @SerializedName("APPT_ID")
    @Expose
    private String apptId;

    @SerializedName("IS_AGENT")
    @Expose
    private String isAgent;

    @SerializedName("BUYER")
    @Expose
    private Buyer buyer;

    public String getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getApptId() {
        return apptId;
    }

    public void setApptId(String apptId) {
        this.apptId = apptId;
    }

    public String getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(String isAgent) {
        this.isAgent = isAgent;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public ArrayList<RecentMessage> getRecentMessages() {
        return recentMessages;
    }

    public void setRecentMessages(ArrayList<RecentMessage> recentMessages) {
        this.recentMessages = recentMessages;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    @SerializedName("RECENT_MESSAGES")
    @Expose
    private ArrayList<RecentMessage> recentMessages;

    @SerializedName("EVENTS")
    @Expose
    private ArrayList<Event> events;

    public class Buyer{
        @SerializedName("ID")
        @Expose
        private String id;

        @SerializedName("EMAIL")
        @Expose
        private String email;

        @SerializedName("PHONE")
        @Expose
        private String phone;

        @SerializedName("FULL_NAME")
        @Expose
        private String fullName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
    public class RecentMessage{
        @SerializedName("ID")
        @Expose
        private String id;

        @SerializedName("THREAD_ID")
        @Expose
        private String threadId;

        @SerializedName("ABBR_BODY")
        @Expose
        private String body;

        @SerializedName("U_FROM_NAME")
        @Expose
        private String uFromName;

        @SerializedName("U_FROM_PHOTO")
        @Expose
        private String uFromPhoto;

        @SerializedName("U_TO_NAME")
        @Expose
        private String uToName;

        @SerializedName("U_TO_PHOTO")
        @Expose
        private String uToPhoto;

        @SerializedName("CREATED_DATETIME")
        @Expose
        private String createdDatetime;

        @SerializedName("READ_STATUS")
        @Expose
        private String readStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getThreadId() {
            return threadId;
        }

        public void setThreadId(String threadId) {
            this.threadId = threadId;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getuFromName() {
            return uFromName;
        }

        public void setuFromName(String uFromName) {
            this.uFromName = uFromName;
        }

        public String getuFromPhoto() {
            return uFromPhoto;
        }

        public void setuFromPhoto(String uFromPhoto) {
            this.uFromPhoto = uFromPhoto;
        }

        public String getuToName() {
            return uToName;
        }

        public void setuToName(String uToName) {
            this.uToName = uToName;
        }

        public String getuToPhoto() {
            return uToPhoto;
        }

        public void setuToPhoto(String uToPhoto) {
            this.uToPhoto = uToPhoto;
        }

        public String getCreatedDatetime() {
            return createdDatetime;
        }

        public void setCreatedDatetime(String createdDatetime) {
            this.createdDatetime = createdDatetime;
        }

        public String getReadStatus() {
            return readStatus;
        }

        public void setReadStatus(String readStatus) {
            this.readStatus = readStatus;
        }
    }
    public class Event{
        @SerializedName("ID")
        @Expose
        private String id;

        @SerializedName("START_TIME")
        @Expose
        private String startTime;

        @SerializedName("END_TIME")
        @Expose
        private String endTime;


        @SerializedName("SRT_DATE")
        @Expose
        private String srtDate;

        @SerializedName("SRT_TIME")
        @Expose
        private String srtTime;

        private Boolean selected;

        public Boolean getSelected() {
            return selected;
        }

        public void setSelected(Boolean selected) {
            this.selected = selected;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getSrtDate() {
            return srtDate;
        }

        public void setSrtDate(String srtDate) {
            this.srtDate = srtDate;
        }

        public String getSrtTime() {
            return srtTime;
        }

        public void setSrtTime(String srtTime) {
            this.srtTime = srtTime;
        }
    }
}
