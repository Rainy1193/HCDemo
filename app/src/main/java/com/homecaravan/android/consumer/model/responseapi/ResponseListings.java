package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseListings extends BaseResponse {

    @SerializedName("data")
    @Expose
    private ResponseListingsData data;

    public ResponseListingsData getData() {
        return data;
    }

    public class ResponseListingsData {
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("data")
        @Expose
        private ArrayList<ListingFull> listingFulls;

        public String getTotal() {
            return total;
        }

        public ArrayList<ListingFull> getListingFulls() {
            return listingFulls;
        }
    }
}
