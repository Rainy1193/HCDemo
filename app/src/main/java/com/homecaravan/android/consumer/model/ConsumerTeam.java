package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsumerTeam extends BaseDataRecyclerView {
    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("EMAIL")
    @Expose
    private String email;
    @SerializedName("STATUS")
    @Expose
    private String status;
    @SerializedName("LOGO")
    @Expose
    private String logo;
    @SerializedName("PHOTO")
    @Expose
    private String photo;
    @SerializedName("FIRST_NAME")
    @Expose
    private String firstName;
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
    private String invitedBy;
    @SerializedName("USER_ID")
    @Expose
    private String userId;
    @SerializedName("COMPANY")
    @Expose
    private String company;
    @SerializedName("LISTING_ID")
    @Expose
    private String listingId;
    @SerializedName("ROLE_ID")
    @Expose
    private String roleId;
    @SerializedName("RECEPTIONIST")
    @Expose
    private String receptionist;
    @SerializedName("MESSAGE_COUNT")
    @Expose
    private String messageCount;
    @SerializedName("COMPANY_ADDRESS_1")
    @Expose
    private String companyAddress1;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getLogo() {
        return logo;
    }

    public String getPhoto() {
        return photo;
    }

    public String getFirstName() {
        return firstName;
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

    public String getInvitedBy() {
        return invitedBy;
    }

    public String getUserId() {
        return userId;
    }

    public String getCompany() {
        return company;
    }

    public String getListingId() {
        return listingId;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getReceptionist() {
        return receptionist;
    }

    public String getMessageCount() {
        return messageCount;
    }

    public String getCompanyAddress1() {
        return companyAddress1;
    }
}
