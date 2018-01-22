package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PolygonSearch extends BaseResponse {
    @SerializedName("data")
    public PolygonSearchData data;

    public class PolygonSearchData {
        @SerializedName("id")
        public String id;
        @SerializedName("data")
        public ArrayList<Location> location;
    }

    public class Location {
        @SerializedName("lat")
        public double lat;
        @SerializedName("lng")
        public double lng;
    }
}
