package com.homecaravan.android.consumer.model;

import io.realm.RealmObject;

public class CollaboratorDB extends RealmObject {
    private String id;
    private String email;
    private String status;
    private String logo;
    private String photo;
    private String firstName;
    private String lastName;
    private String phone;
    private String actionRequired;
    private String actionActive;
    private String invitedBy;
    private String userId;
    private String company;
    private String listingId;
    private String roleId;
    private String receptionist;
    private String messageCount;
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

    @Override
    public String toString() {
        return "CollaboratorDB{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", logo='" + logo + '\'' +
                ", photo='" + photo + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", actionRequired='" + actionRequired + '\'' +
                ", actionActive='" + actionActive + '\'' +
                ", invitedBy='" + invitedBy + '\'' +
                ", userId='" + userId + '\'' +
                ", company='" + company + '\'' +
                ", listingId='" + listingId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", receptionist='" + receptionist + '\'' +
                ", messageCount='" + messageCount + '\'' +
                ", companyAddress1='" + companyAddress1 + '\'' +
                '}';
    }
}
