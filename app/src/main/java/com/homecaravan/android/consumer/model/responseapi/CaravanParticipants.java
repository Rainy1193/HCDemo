package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaravanParticipants {
    @Expose
    @SerializedName("role")
    private String role;
    @Expose
    @SerializedName("lat")
    private String lat;
    @Expose
    @SerializedName("lng")
    private String lng;
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("firstName")
    private String firstName;
    @Expose
    @SerializedName("lastName")
    private String lastName;
    @Expose
    @SerializedName("fullName")
    private String fullName;
    @Expose
    @SerializedName("avatar")
    private String avatar;
    @Expose
    @SerializedName("pn_uid")
    private String pnUid;
    @Expose
    @SerializedName("pn_tid")
    private String pnTid;

    public String getRole() {
        return role;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPnUid() {
        return pnUid;
    }

    public String getPnTid() {
        return pnTid;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setPnUid(String pnUid) {
        this.pnUid = pnUid;
    }

    public void setPnTid(String pnTid) {
        this.pnTid = pnTid;
    }
}