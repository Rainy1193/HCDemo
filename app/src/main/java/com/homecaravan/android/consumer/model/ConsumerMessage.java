package com.homecaravan.android.consumer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsumerMessage {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("regency")
    @Expose
    private String regency;
    @SerializedName("avatar1")
    @Expose
    private String avatar1;
    @SerializedName("avatar2")
    @Expose
    private String avatar2;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("des")
    @Expose
    private String des;
    @SerializedName("isGroup")
    @Expose
    private String isGroup;

    public String getName() {
        return name;
    }

    public String getRegency() {
        return regency;
    }

    public String getAvatar1() {
        return avatar1;
    }

    public String getAvatar2() {
        return avatar2;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getDes() {
        return des;
    }

    public String getIsGroup() {
        return isGroup;
    }
}
