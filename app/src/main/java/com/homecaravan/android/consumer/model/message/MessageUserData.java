package com.homecaravan.android.consumer.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Anh Dao on 9/7/2017.
 */

public class MessageUserData extends RealmObject {

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("createdDatetime")
    private String createdDatetime;
    @Expose
    @SerializedName("modifiedDatetime")
    private String modifiedDatetime;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("data")
    private String data;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("avatar")
    private String avatar;

    public MessageUserData() {
    }

    public MessageUserData(String id, String createdDatetime, String modifiedDatetime, String name, String data, String email, String avatar) {
        this.id = id;
        this.createdDatetime = createdDatetime;
        this.modifiedDatetime = modifiedDatetime;
        this.name = name;
        this.data = data;
        this.email = email;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getModifiedDatetime() {
        return modifiedDatetime;
    }

    public void setModifiedDatetime(String modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
