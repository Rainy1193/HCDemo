package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 5/30/2016.
 */
public class ListingMember extends BaseTeam{

    private String region;

    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("EMAIL")
    @Expose
    private String email;
    @SerializedName("STATUS")
    @Expose
    private String status;
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
    @SerializedName("LISTING_ID")
    @Expose
    private String listingID;
    @SerializedName("ROLE_ID")
    @Expose
    private String roleId;
    @SerializedName("RECEPTIONIST")
    @Expose
    private String receptionist;
    @SerializedName("MESSAGE_COUNT")
    @Expose
    private String messageCount;

    private boolean open;


    public ListingMember(String id, String email, String photo, String firstName, String lastName, String phone, String actionRequired, String actionActive, String invitedBy, String userId, String listingID, String roleId, String receptionist, String messageCount, boolean open) {
        this.id = id;
        this.email = email;
        this.photo = photo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.actionRequired = actionRequired;
        this.actionActive = actionActive;
        this.invitedBy = invitedBy;
        this.userId = userId;
        this.listingID = listingID;
        this.roleId = roleId;
        this.receptionist = receptionist;
        this.messageCount = messageCount;
        this.open = open;
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setListingID(String listingID) {
        this.listingID = listingID;
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

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
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

    public String getListingID() {
        return listingID;
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

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ListingMember{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", actionRequired='" + actionRequired + '\'' +
                ", actionActive='" + actionActive + '\'' +
                ", invitedBy='" + invitedBy + '\'' +
                ", userId='" + userId + '\'' +
                ", listingID='" + listingID + '\'' +
                ", roleId='" + roleId + '\'' +
                ", receptionist='" + receptionist + '\'' +
                ", messageCount='" + messageCount + '\'' +
                ", open=" + open +
                '}';
    }
}
