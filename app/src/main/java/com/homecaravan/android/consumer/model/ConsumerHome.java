package com.homecaravan.android.consumer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsumerHome {
    @SerializedName("lat")
    @Expose
    private String latitude;
    @SerializedName("long")
    @Expose
    private String longitude;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("bed")
    @Expose
    private String bed;
    @SerializedName("bath")
    @Expose
    private String bath;
    @SerializedName("square")
    @Expose
    private String square;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("priceShort")
    @Expose
    private String priceShort;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("status")
    @Expose
    private String status;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
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

    public String getSquare() {
        return square;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceShort() {
        return priceShort;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getStatus() {
        return status;
    }
}
