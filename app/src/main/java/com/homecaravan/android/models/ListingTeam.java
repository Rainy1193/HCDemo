package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 7/8/2016.
 */
public class ListingTeam {
    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("EMAIL")
    @Expose
    private String email;
    @SerializedName("PHOTO")
    @Expose
    private String photo;
    @SerializedName("FIRST_NAME")
    @Expose
    private String fName;
    @SerializedName("LAST_NAME")
    @Expose
    private String lastName;
    @SerializedName("PHONE")
    @Expose
    private String phone;
    @SerializedName("ACTION_REQUIRED")
    @Expose
    private String actionRequired;
    @SerializedName("ACTION_ACTIVE")
    @Expose
    private String actionActive;
    @SerializedName("INVITED_BY")
    @Expose
    private String inviteBy;
    @SerializedName("USER_ID")
    @Expose
    private String userId;
    @SerializedName("LISTING_ID")
    @Expose
    private String listingId;
    @SerializedName("ROLE_ID")
    @Expose
    private String roleId;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }

    public String getfName() {
        return fName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getActionRequired() {
        return actionRequired;
    }

    public String getActionActive() {
        return actionActive;
    }

    public String getInviteBy() {
        return inviteBy;
    }

    public String getUserId() {
        return userId;
    }

    public String getListingId() {
        return listingId;
    }

    public String getRoleId() {
        return roleId;
    }
}
