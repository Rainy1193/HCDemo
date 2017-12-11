package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListingListSearchMap {
    @SerializedName("listing_id")
    @Expose
    private String id;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("favorite")
    @Expose
    private boolean favorite;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("beds")
    @Expose
    private String beds;
    @SerializedName("baths")
    @Expose
    private String baths;
    @SerializedName("living_square")
    @Expose
    private String livingSquare;
    @SerializedName("pool")
    @Expose
    private String pool;
    @SerializedName("thumbnail")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getBeds() {
        return beds;
    }

    public String getBaths() {
        return baths;
    }

    public String getLivingSquare() {
        return livingSquare;
    }

    public String getPool() {
        return pool;
    }

    public String getThumbnail() {
        return image;
    }

    @Override
    public String toString() {
        return "ListingSearchMap{" +
                "id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", favorite='" + favorite + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", beds='" + beds + '\'' +
                ", baths='" + baths + '\'' +
                ", living_square='" + livingSquare + '\'' +
                ", pool='" + pool + '\'' +
                ", thumbnail='" + image + '\'' +
                '}';
    }
}

