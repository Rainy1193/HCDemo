package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homecaravan.android.consumer.model.BaseContact;

public class ContactData extends BaseContact {
    private boolean select;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("_id")
    @Expose
    private String id1;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("status")
    @Expose
    private String status;

    private String type;
    private String firstChar;

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getUser() {
        return user;
    }

    public String getId1() {
        return id1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "createdAt='" + createdAt + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", id='" + id + '\'' +
                ", id1='" + id1 + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", user='" + user + '\'' +
                '}';
    }


}
