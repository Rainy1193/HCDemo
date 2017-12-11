package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseFavorite extends BaseResponse {

    @Expose
    @SerializedName("data")
    private ResponseFavoriteData data;

    public ResponseFavoriteData getData() {
        return data;
    }

    public class ResponseFavoriteData {
        @Expose
        @SerializedName("total")
        private int total;
        @Expose
        @SerializedName("listings")
        private ArrayList<Listing> arrListing;

        public int getTotal() {
            return total;
        }

        public ArrayList<Listing> getArrListing() {
            return arrListing;
        }
    }

}
