package com.homecaravan.android.consumer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseConsumerListingFull {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ResponseData data;
    @SerializedName("code")
    @Expose
    private String code;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ResponseData getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public class ResponseData {
        @SerializedName("total_rows")
        @Expose
        private String totalRows;
        @SerializedName("listings")
        @Expose
        private ArrayList<ConsumerListingFull> listings;

        public String getTotalRows() {
            return totalRows;
        }

        public ArrayList<ConsumerListingFull> getListings() {
            return listings;
        }
    }
}
