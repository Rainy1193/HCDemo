package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Condition {
    @SerializedName("min_pr")
    @Expose
    private  String minPrice;
    @SerializedName("max_pr")
    @Expose
    private  String title;
    @SerializedName("br")
    @Expose
    private  String br;
    @SerializedName("ar")
    @Expose
    private  String ar;
    @SerializedName("lsf")
    @Expose
    private String lsf;
    @SerializedName("ls")
    @Expose
    private String ls;
    @SerializedName("k")
    @Expose
    private  String keyword;
    @SerializedName("ft")
    @Expose
    private String ft;
    @SerializedName("sb")
    @Expose
    private String sb;
    @SerializedName("sm")
    @Expose
    private String sm;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("st")
    @Expose
    private  String st;

}
