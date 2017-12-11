package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FullAddress {
    @SerializedName("one_line")
    @Expose
    private String oneLine;
    @SerializedName("two_line")
    @Expose
    private ArrayList<String> twoLine;

    public String getOneLine() {
        return oneLine;
    }

    public ArrayList<String> getTwoLine() {
        return twoLine;
    }

    @Override
    public String toString() {
        return "FullAddress{" +
                "oneLine='" + oneLine + '\'' +
                ", twoLine=" + twoLine +
                '}';
    }
}
