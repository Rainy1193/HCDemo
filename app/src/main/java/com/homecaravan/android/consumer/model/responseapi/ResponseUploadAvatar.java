package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anh Dao on 11/16/2017.
 */

public class ResponseUploadAvatar extends BaseResponse {
    @SerializedName("data")
    @Expose
    private UploadAvatar data;

    public UploadAvatar getData() {
        return data;
    }

    public class UploadAvatar{
        @SerializedName("fileName")
        @Expose
        private String fileName;

        @SerializedName("url")
        @Expose
        private String url;

        public String getFileName() {
            return fileName;
        }

        public String getUrl() {
            return url;
        }
    }
}
