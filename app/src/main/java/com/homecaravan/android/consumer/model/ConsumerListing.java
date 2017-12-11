package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsumerListing extends BaseDataRecyclerView {
    @SerializedName("image")
    @Expose
    private String image;
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
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public String getImage() {
        return image;
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

    public String getAvatar() {
        return avatar;
    }
}
