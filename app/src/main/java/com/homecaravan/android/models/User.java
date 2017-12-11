package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 3/28/2016.
 */
public class User {
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
	@SerializedName("company")
	@Expose
	private Company company;


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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public String getPreference() {
		return preference;
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
}
