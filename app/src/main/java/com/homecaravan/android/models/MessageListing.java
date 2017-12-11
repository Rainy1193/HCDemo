package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 5/9/2016.
 */
public class MessageListing extends MessageBase {

    private boolean hideTime;
    private boolean isToday;
    private boolean isYesterday;

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("THREAD_ID")
    @Expose
    private String threadId;
    @SerializedName("ABBR_BODY")
    @Expose
    private String abbrBody;
    @SerializedName("U_FROM_NAME")
    @Expose
    private String fromName;
    @SerializedName("U_FROM_PHOTO")
    @Expose
    private String fromPhoto;
    @SerializedName("U_TO_NAME")
    @Expose
    private String toName;
    @SerializedName("U_TO_PHOTO")
    @Expose
    private String toPhoto;
    @SerializedName("UID_FROM")
    @Expose
    private String uidFrom;
    @SerializedName("UID_TO")
    @Expose
    private String uidTo;
    @SerializedName("CREATED_DATETIME")
    @Expose
    private String createdDatetime;
    @SerializedName("READ_STATUS")
    @Expose
    private String readStatus;

    public String getAbbrBody() {
        return abbrBody;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public String getFromName() {
        return fromName;
    }

    public String getFromPhoto() {
        return fromPhoto;
    }

    public String getId() {
        return id;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public String getThreadId() {
        return threadId;
    }

    public String getToName() {
        return toName;
    }

    public String getToPhoto() {
        return toPhoto;
    }

    public String getUidFrom() {
        return uidFrom;
    }

    public String getUidTo() {
        return uidTo;
    }

    public boolean isHideTime() {
        return hideTime;
    }

    public void setHideTime(boolean hideTime) {
        this.hideTime = hideTime;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public boolean isYesterday() {
        return isYesterday;
    }

    public void setYesterday(boolean yesterday) {
        isYesterday = yesterday;
    }

    public MessageListing(String abbrBody, String fromName, String fromPhoto, String createdDatetime) {
        this.abbrBody = abbrBody;
        this.fromName = fromName;
        this.fromPhoto = fromPhoto;
        this.createdDatetime = createdDatetime;
    }

    @Override
    public String toString() {
        return "MessageListing{" +
                "abbrBody='" + abbrBody + '\'' +
                ", id='" + id + '\'' +
                ", threadId='" + threadId + '\'' +
                ", fromName='" + fromName + '\'' +
                ", fromPhoto='" + fromPhoto + '\'' +
                ", toName='" + toName + '\'' +
                ", toPhoto='" + toPhoto + '\'' +
                ", uidFrom='" + uidFrom + '\'' +
                ", uidTo='" + uidTo + '\'' +
                ", createdDatetime='" + createdDatetime + '\'' +
                ", readStatus='" + readStatus + '\'' +
                '}';
    }
}
