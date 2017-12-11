package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 5/25/2016.
 */
public class ResponseCreateListing {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ResponseCreate data;
    @SerializedName("code")
    @Expose
    private int code;

    public String isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ResponseCreate getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public class ResponseCreate
    {
        @SerializedName("LISTING_ID")
        @Expose
        private String id;
        @SerializedName("LKEY")
        @Expose
        private String lkey;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLkey() {
            return lkey;
        }

        public void setLkey(String lkey) {
            this.lkey = lkey;
        }
    }
}
