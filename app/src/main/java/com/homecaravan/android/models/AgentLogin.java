package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 9/6/2016.
 */
public class AgentLogin {
    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("EMAIL")
    @Expose
    private String email;
    @SerializedName("FIRST_NAME")
    @Expose
    private String firstName;
    @SerializedName("LAST_NAME")
    @Expose
    private String lastName;
    @SerializedName("PHONE")
    @Expose
    private String phone;
    @SerializedName("PHOTO")
    @Expose
    private String photo;
    @SerializedName("HAS_AGENT")
    @Expose
    private String hasAgent;
    @SerializedName("AGENT_EMAIL")
    @Expose
    private String agentEmail;
    @SerializedName("TOKEN")
    @Expose
    private String token;
    @SerializedName("COMMUNICATION_PREFERENCE")
    @Expose
    private String preference;
    @SerializedName("SYNC_TYPE")
    @Expose
    private String calendar;
    @SerializedName("BUSINESS_ROLE")
    @Expose
    private String type;
    @SerializedName("REGION_CODE")
    @Expose
    private String region;
    @SerializedName("TIMEZONE")
    @Expose
    private String timeZone;

    @SerializedName("COMPANY_TITLE")
    @Expose
    private String title;
    @SerializedName("COMPANY_STATE")
    @Expose
    private String state;
    @SerializedName("COMPANY_ADDRESS_1")
    @Expose
    private String add1;
    @SerializedName("COMPANY_ADDRESS_2")
    @Expose
    private String add2;
    @SerializedName("COMPANY_CITY")
    @Expose
    private String city;
    @SerializedName("COMPANY_ZIP")
    @Expose
    private String zip;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getHasAgent() {
        return hasAgent;
    }

    public void setHasAgent(String hasAgent) {
        this.hasAgent = hasAgent;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
