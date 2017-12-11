package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vankhoadesign on 7/3/16.
 */
public class InviteReturn {
        @SerializedName("CLIENT_ID")
        @Expose
        private String id;
        @SerializedName("FULL_NAME")
        @Expose
        private String fullName;
        @SerializedName("EMAIL")
        @Expose
        private String email;
        @SerializedName("PHONE")
        @Expose
        private String phone;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;

    }

    public String getPhone() {
        return phone;
    }

}
