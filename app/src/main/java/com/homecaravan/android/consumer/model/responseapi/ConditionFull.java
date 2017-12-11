package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ConditionFull {
    @SerializedName("min_pr")
    @Expose
    private String minPrice;
    @SerializedName("max_pr")
    @Expose
    private String maxPrice;
    @SerializedName("br")
    @Expose
    private String br;
    @SerializedName("ar")
    @Expose
    private String ar;
    @SerializedName("lsf")
    @Expose
    private String lsf;
    @SerializedName("ls")
    @Expose
    private String ls;
    @SerializedName("k")
    @Expose
    private String keyword;
    @SerializedName("ft")
    @Expose
    private Object ft;
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
    private String st;
    @SerializedName("min_ls")
    @Expose
    private String minLs;
    @SerializedName("max_ls")
    @Expose
    private String maxLs;
    @SerializedName("min_lsf")
    @Expose
    private String minLsf;
    @SerializedName("max_lsf")
    @Expose
    private String maxLsf;
    @SerializedName("min_yb")
    @Expose
    private String minYb;
    @SerializedName("max_yb")
    @Expose
    private String maxYb;
    @SerializedName("dc")
    @Expose
    private String dc;
    @SerializedName("ne")
    @Expose
    private String ne;
    @SerializedName("sw")
    @Expose
    private String sw;
    @SerializedName("pt")
    @Expose
    private String pt;
    @SerializedName("ra")
    @Expose
    private String ra;

    private ArrayList<String> arrFt;

    private String sFt;

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public ArrayList<String> getArrFt() {
        return arrFt;
    }

    public void setArrFt(ArrayList<String> arrFt) {
        this.arrFt = arrFt;
    }

    public String getsFt() {
        return sFt;
    }

    public void setsFt(String sFt) {
        this.sFt = sFt;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public String getBr() {
        return br;
    }

    public String getAr() {
        return ar;
    }

    public String getLsf() {
        return lsf;
    }

    public String getLs() {
        return ls;
    }

    public String getKeyword() {
        return keyword;
    }

    public Object getFt() {
        return ft;
    }

    public String getSb() {
        return sb;
    }

    public String getSm() {
        return sm;
    }

    public String getSrc() {
        return src;
    }

    public String getSt() {
        return st;
    }

    public String getMinLs() {
        return minLs;
    }

    public String getMaxLs() {
        return maxLs;
    }

    public String getMinLsf() {
        return minLsf;
    }

    public String getMaxLsf() {
        return maxLsf;
    }

    public String getMinYb() {
        return minYb;
    }

    public String getMaxYb() {
        return maxYb;
    }

    public String getDc() {
        return dc;
    }

    public String getNe() {
        return ne;
    }

    public String getSw() {
        return sw;
    }

    public String getPt() {
        return pt;
    }

    public String getRa() {
        return ra;
    }

    @Override
    public String toString() {
        return "ConditionFull{" +
                "minPrice='" + minPrice + '\'' +
                ", title='" + maxPrice + '\'' +
                ", br='" + br + '\'' +
                ", ar='" + ar + '\'' +
                ", lsf='" + lsf + '\'' +
                ", ls='" + ls + '\'' +
                ", keyword='" + keyword + '\'' +
                ", ft=" + ft +
                ", sb='" + sb + '\'' +
                ", sm='" + sm + '\'' +
                ", src='" + src + '\'' +
                ", st='" + st + '\'' +
                ", minLs='" + minLs + '\'' +
                ", maxLs='" + maxLs + '\'' +
                ", minLsf='" + minLsf + '\'' +
                ", maxLsf='" + maxLsf + '\'' +
                ", minYb='" + minYb + '\'' +
                ", maxYb='" + maxYb + '\'' +
                ", dc='" + dc + '\'' +
                ", ne='" + ne + '\'' +
                ", sw='" + sw + '\'' +
                ", pt=" + pt +
                ", ra='" + ra + '\'' +
                '}';
    }
}
