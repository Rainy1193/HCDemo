package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 8/22/2016.
 */
public class ResponseChangePassword {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Token data;
    @SerializedName("code")
    @Expose
    private int code;

    public String isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Token getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public class Token {
        @SerializedName("token")
        @Expose
        private String token;

        public String getToken() {
            return token;
        }
    }

}
