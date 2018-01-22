package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PolygonDetail extends BaseResponse {
    @SerializedName("data")
    public PolygonDetailData data;

    public class PolygonDetailData {
        @SerializedName("id")
        public String id;
        @SerializedName("data")
        public ArrayList<Location> locations;

    }

    public class Location {
        @SerializedName("lat")
        public double lat;
        @SerializedName("lng")
        public double lng;
    }
}
