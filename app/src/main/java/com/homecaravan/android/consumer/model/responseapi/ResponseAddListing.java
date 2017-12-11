package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAddListing extends BaseResponse {
    @Expose
    @SerializedName("data")
    private DataAddListing data;

    public DataAddListing getData() {
        return data;
    }

    public class DataAddListing {
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("address")
        private String address;
        @Expose
        @SerializedName("titile")
        private String title;
        @Expose
        @SerializedName("full_name")
        private String fullName;
        @Expose
        @SerializedName("status")
        private String status;
        @Expose
        @SerializedName("created_datetime")
        private String timeCreate;

        public String getId() {
            return id;
        }

        public String getAddress() {
            return address;
        }

        public String getTitle() {
            return title;
        }

        public String getFullName() {
            return fullName;
        }

        public String getStatus() {
            return status;
        }

        public String getTimeCreate() {
            return timeCreate;
        }
    }

}
