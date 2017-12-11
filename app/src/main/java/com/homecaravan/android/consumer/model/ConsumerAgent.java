package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsumerAgent {
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

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setActionRequired(String actionRequired) {
        this.actionRequired = actionRequired;
    }

    public void setActionActive(String actionActive) {
        this.actionActive = actionActive;
    }

    public void setInvitedBy(String invitedBy) {
        this.invitedBy = invitedBy;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setReceptionist(String receptionist) {
        this.receptionist = receptionist;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }

    public void setCompanyAddress1(String companyAddress1) {
        this.companyAddress1 = companyAddress1;
    }
}
