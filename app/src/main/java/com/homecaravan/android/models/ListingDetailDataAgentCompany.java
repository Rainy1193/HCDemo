package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 5/3/2016.
 */
public class ListingDetailDataAgentCompany {
    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("TITLE")
    @Expose
    private String title;
    @SerializedName("ADDRESS_1")
    @Expose
    private String add1;
    @SerializedName("ADDRESS_2")
    @Expose
    private String add2;
    @SerializedName("CITY")
    @Expose
    private String city;
    @SerializedName("STATE")
    @Expose
    private String state;
    @SerializedName("ZIP")
    @Expose
    private String zip;
    @SerializedName("PHONE")
    @Expose
    private String phone;
    @SerializedName("LOGO")
    @Expose
    private String logo;
    @SerializedName("CREATED_DATETIME")
    @Expose
    private String createdDatetime;
    @SerializedName("CREATED_BY")
    @Expose
    private String createdBy;
    @SerializedName("MODIFIED_DATETIME")
    @Expose
    private String modifiedDatetime;
    @SerializedName("MODIFIED_BY")
    @Expose
    private String modifiedBy;

    public String getAdd1() {
        return add1;
    }

    public String getAdd2() {
        return add2;
    }

    public String getCity() {
        return city;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public String getId() {
        return id;
    }

    public String getLogo() {
        return logo;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public String getModifiedDatetime() {
        return modifiedDatetime;
    }

    public String getPhone() {
        return phone;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getZip() {
        return zip;
    }
}
