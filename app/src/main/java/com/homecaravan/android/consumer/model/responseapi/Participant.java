package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Participant {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("pn_uid")
    @Expose
    private String pnUid;
    @SerializedName("pn_tid")
    @Expose
    private String pnTid;
    @SerializedName("company")
    @Expose
    private ArrayList<Companies> company;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPnUid() {
        return pnUid;
    }

    public void setPnUid(String pnUid) {
        this.pnUid = pnUid;
    }

    public String getPnTid() {
        return pnTid;
    }

    public void setPnTid(String pnTid) {
        this.pnTid = pnTid;
    }

    public ArrayList<Companies> getCompany() {
        return company;
    }

    public void setCompany(ArrayList<Companies> company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id='" + id + '\'' +
                ", role='" + role + '\'' +
                ", weight='" + weight + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", pnUid='" + pnUid + '\'' +
                ", pnTid='" + pnTid + '\'' +
                '}';
    }
}
