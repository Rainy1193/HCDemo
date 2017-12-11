package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 5/11/2016.
 */
public class ResponseSuggestionPickup {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private SuggestionPickup data;
    @SerializedName("code")
    @Expose
    private String code;

    public String getSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() { return message;
    }
    public SuggestionPickup getData() { return data; }
}
