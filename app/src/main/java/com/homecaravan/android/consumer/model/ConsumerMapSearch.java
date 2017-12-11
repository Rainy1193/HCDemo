package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsumerMapSearch {

    @SerializedName("id")
    private String id;

    @SerializedName("lat")
    @Expose
    private String latitude;
    @SerializedName("long")
    @Expose
    private String longitude;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_info")
    @Expose
    private String statusInfo;
    @SerializedName("image_total")
    @Expose
    private String imageTotal;
    @SerializedName("living")
    @Expose
    private String living;
    @SerializedName("bed")
    @Expose
    private String bed;
    @SerializedName("bath")
    @Expose
    private String bath;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;

    public String getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public String getImageTotal() {
        return imageTotal;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getBed() {
        return bed;
    }

    public String getBath() {
        return bath;
    }

    public String getLiving() {
        return living;
    }
}
