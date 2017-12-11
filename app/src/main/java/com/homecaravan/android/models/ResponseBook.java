package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 5/5/2016.
 */
public class ResponseBook {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private BookData bookData;
    @SerializedName("code")
    @Expose
    private String code;

    public BookData getBookData() {
        return bookData;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getSuccess() {
        return success;
    }
}
