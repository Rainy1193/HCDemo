package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anh Dao on 11/16/2017.
 */

public class ResponseChangePassword extends BaseResponse {
    @SerializedName("data")
    @Expose
    private ChangePassword data;

    public ChangePassword getData() {
        return data;
    }

    public class ChangePassword{
        @SerializedName("token")
        @Expose
        private String token;

        public String getToken() {
            return token;
        }
    }
}
