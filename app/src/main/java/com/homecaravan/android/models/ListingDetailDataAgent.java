package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 5/3/2016.
 */
public class ListingDetailDataAgent {
    @SerializedName("ID")
    @Expose
    private String idAgent;
    @SerializedName("EMAIL")
    @Expose
    private String emailAgent;
    @SerializedName("AUTHENTICATION_CODE")
    @Expose
    private String authenCodeAgent;
    @SerializedName("BUSINESS_ROLE")
    @Expose
    private String businessRoleAgent;
    @SerializedName("LAST_ROLE_ASSIGNED_BY")
    @Expose
    private String lastRoleAgent;
    @SerializedName("FORGOTTEN_PASSWORD_CODE")
    @Expose
    private String forgottenPasswordCodeAgent;
    @SerializedName("FORGOTTEN_PASSWORD_TIME")
    @Expose
    private String forgottenPasswordTimeAgent;
    @SerializedName("LAST_LOGIN_ATTEMPT")
    @Expose
    private String lastLoginAttemptAgent;
    @SerializedName("STATUS")
    @Expose
    private String statusAgent;
    @SerializedName("FIRST_NAME")
    @Expose
    private String firstNameAgent;
    @SerializedName("LAST_NAME")
    @Expose
    private String lastNameAgent;
    @SerializedName("PHONE")
    @Expose
    private String phoneAgent;
    @SerializedName("OFFICE_PHONE")
    @Expose
    private String officePhoneAgent;
    @SerializedName("HAS_AGENT")
    @Expose
    private String hasAgent;
    @SerializedName("AGENT_EMAIL")
    @Expose
    private String agentEmail;
    @SerializedName("TIMEZONE")
    @Expose
    private String timeZoneAgent;
    @SerializedName("SYNC_TYPE")
    @Expose
    private String syncTypeAgent;
    @SerializedName("COMMUNICATION_PREFERENCE")
    @Expose
    private String communicationAgent;
    @SerializedName("PHOTO")
    @Expose
    private String photoAgent;
    @SerializedName("DEVICE_TOKEN")
    @Expose
    private String deviceTokenAgent;
    @SerializedName("FIRST_LOGIN")
    @Expose
    private String firstLoginAgent;
    @SerializedName("CREATED_DATETIME")
    @Expose
    private String createDateTimeAgent;
    @SerializedName("CREATED_BY")
    @Expose
    private String createdByAgent;
    @SerializedName("MODIFIED_DATETIME")
    @Expose
    private String modifiedDateTimeAgent;
    @SerializedName("MODIFIED_BY")
    @Expose
    private String modifiedByAgent;
    @SerializedName("FB_ID")
    @Expose
    private String fbIdAgent;
    @SerializedName("LINKED_ID")
    @Expose
    private String linkedIdAgent;
    @SerializedName("AGENT_ID")
    @Expose
    private String agentId;
    @SerializedName("ROLE_TYPE")
    @Expose
    private String roleTypeAgent;
    @SerializedName("COMPANY_ID")
    @Expose
    private String companyIdAgent;
    @SerializedName("HISTORY")
    @Expose
    private History historyAgent;
    @SerializedName("DEFAULT_CLIENT")
    @Expose
    private String defaultClientAgent;
    @SerializedName("COMPANY_TITLE")
    @Expose
    private String title;
    @SerializedName("COMPANY_ADDRESS_1")
    @Expose
    private String add1;
    @SerializedName("COMPANY_ADDRESS_2")
    @Expose
    private String add2;
    @SerializedName("COMPANY_CITY")
    @Expose
    private String city;
    @SerializedName("COMPANY_STATE")
    @Expose
    private String state;
    @SerializedName("COMPANY_ZIP")
    @Expose
    private String zip;


    public String getAgentEmail() {
        return agentEmail;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getAuthenCodeAgent() {
        return authenCodeAgent;
    }

    public String getBusinessRoleAgent() {
        return businessRoleAgent;
    }

    public String getCommunicationAgent() {
        return communicationAgent;
    }

    public String getCompanyIdAgent() {
        return companyIdAgent;
    }

    public String getCreateDateTimeAgent() {
        return createDateTimeAgent;
    }

    public String getCreatedByAgent() {
        return createdByAgent;
    }

    public String getDefaultClientAgent() {
        return defaultClientAgent;
    }

    public String getDeviceTokenAgent() {
        return deviceTokenAgent;
    }

    public String getEmailAgent() {
        return emailAgent;
    }

    public String getFbIdAgent() {
        return fbIdAgent;
    }

    public String getFirstLoginAgent() {
        return firstLoginAgent;
    }

    public String getFirstNameAgent() {
        return firstNameAgent;
    }

    public String getForgottenPasswordCodeAgent() {
        return forgottenPasswordCodeAgent;
    }

    public String getForgottenPasswordTimeAgent() {
        return forgottenPasswordTimeAgent;
    }

    public String getHasAgent() {
        return hasAgent;
    }

    public History getHistoryAgent() {
        return historyAgent;
    }

    public String getIdAgent() {
        return idAgent;
    }

    public String getLastLoginAttemptAgent() {
        return lastLoginAttemptAgent;
    }

    public String getLastNameAgent() {
        return lastNameAgent;
    }

    public String getLastRoleAgent() {
        return lastRoleAgent;
    }

    public String getLinkedIdAgent() {
        return linkedIdAgent;
    }

    public String getModifiedByAgent() {
        return modifiedByAgent;
    }

    public String getModifiedDateTimeAgent() {
        return modifiedDateTimeAgent;
    }

    public String getOfficePhoneAgent() {
        return officePhoneAgent;
    }

    public String getPhoneAgent() {
        return phoneAgent;
    }

    public String getPhotoAgent() {
        return photoAgent;
    }

    public String getRoleTypeAgent() {
        return roleTypeAgent;
    }

    public String getStatusAgent() {
        return statusAgent;
    }

    public String getSyncTypeAgent() {
        return syncTypeAgent;
    }

    public String getTimeZoneAgent() {
        return timeZoneAgent;
    }

    public void setDefaultClientAgent(String defaultClientAgent) {
        this.defaultClientAgent = defaultClientAgent;
    }

    public void setEmailAgent(String emailAgent) {
        this.emailAgent = emailAgent;
    }

    public void setAuthenCodeAgent(String authenCodeAgent) {
        this.authenCodeAgent = authenCodeAgent;
    }

    public void setBusinessRoleAgent(String businessRoleAgent) {
        this.businessRoleAgent = businessRoleAgent;
    }

    public void setLastRoleAgent(String lastRoleAgent) {
        this.lastRoleAgent = lastRoleAgent;
    }

    public void setForgottenPasswordCodeAgent(String forgottenPasswordCodeAgent) {
        this.forgottenPasswordCodeAgent = forgottenPasswordCodeAgent;
    }

    public void setForgottenPasswordTimeAgent(String forgottenPasswordTimeAgent) {
        this.forgottenPasswordTimeAgent = forgottenPasswordTimeAgent;
    }

    public void setLastLoginAttemptAgent(String lastLoginAttemptAgent) {
        this.lastLoginAttemptAgent = lastLoginAttemptAgent;
    }

    public void setStatusAgent(String statusAgent) {
        this.statusAgent = statusAgent;
    }

    public void setFirstNameAgent(String firstNameAgent) {
        this.firstNameAgent = firstNameAgent;
    }

    public void setLastNameAgent(String lastNameAgent) {
        this.lastNameAgent = lastNameAgent;
    }

    public void setPhoneAgent(String phoneAgent) {
        this.phoneAgent = phoneAgent;
    }

    public void setOfficePhoneAgent(String officePhoneAgent) {
        this.officePhoneAgent = officePhoneAgent;
    }

    public void setHasAgent(String hasAgent) {
        this.hasAgent = hasAgent;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public void setTimeZoneAgent(String timeZoneAgent) {
        this.timeZoneAgent = timeZoneAgent;
    }

    public void setSyncTypeAgent(String syncTypeAgent) {
        this.syncTypeAgent = syncTypeAgent;
    }

    public void setCommunicationAgent(String communicationAgent) {
        this.communicationAgent = communicationAgent;
    }

    public void setPhotoAgent(String photoAgent) {
        this.photoAgent = photoAgent;
    }

    public void setDeviceTokenAgent(String deviceTokenAgent) {
        this.deviceTokenAgent = deviceTokenAgent;
    }

    public void setFirstLoginAgent(String firstLoginAgent) {
        this.firstLoginAgent = firstLoginAgent;
    }

    public void setCreateDateTimeAgent(String createDateTimeAgent) {
        this.createDateTimeAgent = createDateTimeAgent;
    }

    public void setCreatedByAgent(String createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public void setModifiedDateTimeAgent(String modifiedDateTimeAgent) {
        this.modifiedDateTimeAgent = modifiedDateTimeAgent;
    }

    public void setModifiedByAgent(String modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public void setFbIdAgent(String fbIdAgent) {
        this.fbIdAgent = fbIdAgent;
    }

    public void setLinkedIdAgent(String linkedIdAgent) {
        this.linkedIdAgent = linkedIdAgent;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public void setRoleTypeAgent(String roleTypeAgent) {
        this.roleTypeAgent = roleTypeAgent;
    }

    public void setCompanyIdAgent(String companyIdAgent) {
        this.companyIdAgent = companyIdAgent;
    }

    public void setHistoryAgent(History historyAgent) {
        this.historyAgent = historyAgent;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
    }

    public String getTitle() {
        return title;
    }

    public String getAdd1() {
        return add1;
    }

    public String getAdd2() {
        return add2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }
}
