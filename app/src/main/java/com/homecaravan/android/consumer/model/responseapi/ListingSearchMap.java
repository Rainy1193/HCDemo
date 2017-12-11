package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListingSearchMap {
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
    @SerializedName("square_feet")
    @Expose
    private String livingSquare;
    @SerializedName("pool")
    @Expose
    private String pool;
    @SerializedName("image")
    @Expose
    private String thumbnail;

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
        return thumbnail;
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
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public void setBaths(String baths) {
        this.baths = baths;
    }

    public void setLivingSquare(String livingSquare) {
        this.livingSquare = livingSquare;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
