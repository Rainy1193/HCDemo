package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Company {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private AddressCompany address;
    @SerializedName("logo")
    @Expose
    private String logo;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public AddressCompany getAddress() {
        return address;
    }

    public String getLogo() {
        return logo;
    }

    public class AddressCompany {
        @Expose
        @SerializedName("address1")
        private String address1;
        @Expose
        @SerializedName("address2")
        private String address2;
        @Expose
        @SerializedName("city")
        private String city;
        @Expose
        @SerializedName("state")
        private String state;
        @Expose
        @SerializedName("zip")
        private String zip;
        @Expose
        @SerializedName("country")
        private String country;
        @Expose
        @SerializedName("lng")
        private String lng;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("fullAddress")
        private FullAddressUser fullAddress;

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

        public String getLng() {
            return lng;
        }

        public String getLat() {
            return lat;
        }

        public FullAddressUser getFullAddress() {
            return fullAddress;
        }

        @Override
        public String toString() {
            return "AddressUser{" +
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

    public class FullAddressUser {
        @Expose
        @SerializedName("one_line")
        private String oneLine;
        @Expose
        @SerializedName("two_line")
        private ArrayList<String> twoLine;

        public String getOneLine() {
            return oneLine;
        }

        public ArrayList<String> getTwoLine() {
            return twoLine;
        }

        @Override
        public String toString() {
            return "FullAddressUser{" +
                    "oneLine='" + oneLine + '\'' +
                    ", twoLine=" + twoLine +
                    '}';
        }
    }
}
