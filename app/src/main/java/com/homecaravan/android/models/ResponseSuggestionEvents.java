package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by vankhoadesign on 9/28/16.
 */

public class ResponseSuggestionEvents {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<SuggestionEvent>  data;

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
    public ArrayList<SuggestionEvent> getData() { return data; }
}
