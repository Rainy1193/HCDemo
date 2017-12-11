package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("lng")
    @Expose
    private double lng;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("fullAddress")
    @Expose
    private FullAddress fullAddress;


    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public FullAddress getFullAddress() {
        return fullAddress;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", fullAddress=" + fullAddress +
                '}';
    }
}
