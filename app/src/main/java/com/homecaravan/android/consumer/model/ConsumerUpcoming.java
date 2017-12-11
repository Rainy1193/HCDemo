package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsumerUpcoming {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;



    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
