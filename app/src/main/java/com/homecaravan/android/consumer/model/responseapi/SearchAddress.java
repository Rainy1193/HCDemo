package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchAddress extends BaseResponse {

    @SerializedName("data")
    public DataAddress dataAddress;

    public class DataAddress {
        @SerializedName("address")
        public String address;
        @SerializedName("bounds")
        public Bounds bounds;
        @SerializedName("area_data")
        public Area areaData;
    }

    public class Bounds {
        @SerializedName("northeast")
        public Northeast northeast;
        @SerializedName("southwest")
        public Southwest southwest;
    }

    public class Area {
        @SerializedName("id")
        public String id;
        @SerializedName("name")
        public String name;
        @SerializedName("url_key")
        public String urlKey;
        @SerializedName("state")
        public String state;
        @SerializedName("county")
        public String county;
        @SerializedName("type")
        public String type;
        @SerializedName("data")
        public DataArea data;

    }

    public class DataArea {
        @SerializedName("type")
        public String type;
        @SerializedName("coordinates")
        public ArrayList<ArrayList<ArrayList<Double>>> arrLocation;
    }

    public class Northeast {
        @SerializedName("lat")
        public double lat;
        @SerializedName("lng")
        public double lng;
    }

    public class Southwest {
        @SerializedName("lat")
        public double lat;
        @SerializedName("lng")
        public double lng;
    }
}
