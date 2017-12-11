package com.homecaravan.android.consumer.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Anh Dao on 9/7/2017.
 */

public class MessageThreadView extends RealmObject {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("photo")
    private String photo;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("createdDatetime")
    private String createdDatetime;
    @Expose
    @SerializedName("type")
    private String type;

    public MessageThreadView() {
    }

    public MessageThreadView(String id, String name, String photo, String content, String createdDatetime, String type) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.content = content;
        this.createdDatetime = createdDatetime;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
