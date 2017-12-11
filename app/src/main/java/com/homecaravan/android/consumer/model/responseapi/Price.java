package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("text")
    @Expose
    private String text;

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Price{" +
                "value='" + value + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
