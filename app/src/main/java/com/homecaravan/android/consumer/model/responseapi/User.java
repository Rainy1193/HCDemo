package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {

    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("regionCode")
    private String regionCode;
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("firstName")
    private String firstName;
    @Expose
    @SerializedName("lastName")
    private String lastName;
    @Expose
    @SerializedName("fullName")
    private String fullName;
    @Expose
    @SerializedName("avatar")
    private String avatar;
    @Expose
    @SerializedName("pn_uid")
    private String pnUid;
    @Expose
    @SerializedName("pn_tid")
    private String pnTid;
    @Expose
    @SerializedName("agent")
    private AgentUser agent;
    @Expose
    @SerializedName("company")
    private ArrayList<Companies> company;

    @Expose
    @SerializedName("url")
    private String profileUrl;
    @Expose
    @SerializedName("video")
    private String videoUrl;
    @Expose
    @SerializedName("intro")
    private String intro;
    @Expose
    @SerializedName("aboutMe")
    private String aboutMe;
    @Expose
    @SerializedName("facebook")
    private String facebookUrl;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("businessRole")
    private String businessRole;
    @Expose
    @SerializedName("receiveNotifications")
    private String receiveNotifications;
    @Expose
    @SerializedName("newHomesNotifications")
    private String newHomesNotifications;
    @Expose
    @SerializedName("emailSmsNotifications")
    private String emailSmsNotifications;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public AgentUser getAgent() {
        return agent;
    }

    public void setAgent(AgentUser agent) {
        this.agent = agent;
    }

    public ArrayList<Companies> getCompany() {
        return company;
    }

    public void setCompany(ArrayList<Companies> company) {
        this.company = company;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessRole() {
        return businessRole;
    }

    public void setBusinessRole(String businessRole) {
        this.businessRole = businessRole;
    }

    public String getReceiveNotifications() {
        return receiveNotifications;
    }

    public void setReceiveNotifications(String receiveNotifications) {
        this.receiveNotifications = receiveNotifications;
    }

    public String getNewHomesNotifications() {
        return newHomesNotifications;
    }

    public void setNewHomesNotifications(String newHomesNotifications) {
        this.newHomesNotifications = newHomesNotifications;
    }

    public String getEmailSmsNotifications() {
        return emailSmsNotifications;
    }

    public void setEmailSmsNotifications(String emailSmsNotifications) {
        this.emailSmsNotifications = emailSmsNotifications;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", pnUid='" + pnUid + '\'' +
                ", pnTid='" + pnTid + '\'' +
                ", agent=" + agent +
                ", company=" + company +
                '}';
    }

    public class AgentUser {
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("firstName")
        private String firstName;
        @Expose
        @SerializedName("lastName")
        private String lastName;
        @Expose
        @SerializedName("fullName")
        private String fullName;
        @Expose
        @SerializedName("avatar")
        private String avatar;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("phone")
        private String phone;
        @Expose
        @SerializedName("pn_uid")
        private String pnUid;
        @Expose
        @SerializedName("pn_tid")
        private String pnTid;
        @Expose
        @SerializedName("company")
        private ArrayList<Companies> company;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
            return "AgentUser{" +
                    "id='" + id + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", fullName='" + fullName + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }
}