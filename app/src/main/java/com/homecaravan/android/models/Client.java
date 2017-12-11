package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 7/14/2016.
 */
public class Client {
    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("EMAIL")
    @Expose
    private String email;
    @SerializedName("PASSWORD")
    @Expose
    private String password;
    @SerializedName("AUTHENTICATION_CODE")
    @Expose
    private String authenticationCode;
    @SerializedName("BUSINESS_ROLE")
    @Expose
    private String businessRole;
    @SerializedName("LAST_ROLE_ASSIGNED_BY")
    @Expose
    private String lastRole;
    @SerializedName("SALT")
    @Expose
    private String salt;
    @SerializedName("FORGOTTEN_PASSWORD_CODE")
    @Expose
    private String forgottenPasswordCode;
    @SerializedName("FORGOTTEN_PASSWORD_TIME")
    @Expose
    private String forgottenPasswordTime;

    @SerializedName("LAST_LOGIN_ATTEMPT")
    @Expose
    private String lastLoginAttempt;
    @SerializedName("STATUS")
    @Expose
    private String status;
    @SerializedName("FIRST_NAME")
    @Expose
    private String firstName;
    @SerializedName("LAST_NAME")
    @Expose
    private String lastName;
    @SerializedName("PHONE")
    @Expose
    private String phone;
    @SerializedName("OFFICE_PHONE")
    @Expose
    private String officePhone;
    @SerializedName("HAS_AGENT")
    @Expose
    private String hasAgent;
    @SerializedName("AGENT_EMAIL")
    @Expose
    private String agentEmail;
    @SerializedName("TIMEZONE")
    @Expose
    private String timeZone;
    @SerializedName("SYNC_TYPE")
    @Expose
    private String syncType;
    @SerializedName("COMMUNICATION_PREFERENCE")
    @Expose
    private String communication;
    @SerializedName("PHOTO")
    @Expose
    private String photo;
    @SerializedName("DEVICE_TOKEN")
    @Expose
    private String deviceToken;
    @SerializedName("FIRST_LOGIN")
    @Expose
    private String firstLogin;
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
    @SerializedName("FB_ID")
    @Expose
    private String fbId;
    @SerializedName("LINKED_ID")
    @Expose
    private String linkedId;
    @SerializedName("AGENT_ID")
    @Expose
    private String agentId;
    @SerializedName("ROLE_TYPE")
    @Expose
    private String roleType;
    @SerializedName("COMPANY_ID")
    @Expose
    private String companyId;
    @SerializedName("HISTORY")
    @Expose
    private History history;
    @SerializedName("DEFAULT_CLIENT")
    @Expose
    private String defaultClient;

    private String name;

    public Client(String id, String email, String name, String phone,String status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.status=status;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthenticationCode() {
        return authenticationCode;
    }

    public String getBusinessRole() {
        return businessRole;
    }

    public String getLastRole() {
        return lastRole;
    }

    public String getSalt() {
        return salt;
    }

    public String getForgottenPasswordCode() {
        return forgottenPasswordCode;
    }

    public String getForgottenPasswordTime() {
        return forgottenPasswordTime;
    }

    public String getLastLoginAttempt() {
        return lastLoginAttempt;
    }

    public String getStatus() {
        return status;
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

    public String getOfficePhone() {
        return officePhone;
    }

    public String getHasAgent() {
        return hasAgent;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getSyncType() {
        return syncType;
    }

    public String getCommunication() {
        return communication;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getFirstLogin() {
        return firstLogin;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getModifiedDateTime() {
        return modifiedDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public String getFbId() {
        return fbId;
    }

    public String getLinkedId() {
        return linkedId;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getRoleType() {
        return roleType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public History getHistory() {
        return history;
    }

    public String getDefaultClient() {
        return defaultClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}