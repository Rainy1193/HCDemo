package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 4/4/2016.
 */
public class Company {
    @SerializedName("ID")
    @Expose
    private String idCompany;
    @SerializedName("TITLE")
    @Expose
    private String titleCompany;
    @SerializedName("ADDRESS_1")
    @Expose
    private String add1Company;
    @SerializedName("ADDRESS_2")
    @Expose
    private String add2Company;
    @SerializedName("CITY")
    @Expose
    private String cityCompany;
    @SerializedName("STATE")
    @Expose
    private String stateCompany;
    @SerializedName("ZIP")
    @Expose
    private String zipCompany;
    @SerializedName("PHONE")
    @Expose
    private String phoneCompany;
    @SerializedName("LOGO")
    @Expose
    private String logoCompany;
    @SerializedName("CREATED_DATETIME")
    @Expose
    private String createDateTimeCompany;
    @SerializedName("CREATED_BY")
    @Expose
    private String createByCompany;
    @SerializedName("MODIFIED_DATETIME")
    @Expose
    private String modifiedDateTimeCompany;
    @SerializedName("MODIFIED_BY")
    @Expose
    private String modifiedByCompany;
    @SerializedName("REGION_CODE")
    @Expose
    private String regionCompany;
    public Company(String idCompany, String titleCompany, String add1Company, String add2Company, String cityCompany, String stateCompany, String zipCompany, String phoneCompany, String logoCompany, String createDateTimeCompany, String createByCompany, String modifiedDateTimeCompany, String modifiedByCompany) {
        this.idCompany = idCompany;
        this.titleCompany = titleCompany;
        this.add1Company = add1Company;
        this.add2Company = add2Company;
        this.cityCompany = cityCompany;
        this.stateCompany = stateCompany;
        this.zipCompany = zipCompany;
        this.phoneCompany = phoneCompany;
        this.logoCompany = logoCompany;
        this.createDateTimeCompany = createDateTimeCompany;
        this.createByCompany = createByCompany;
        this.modifiedDateTimeCompany = modifiedDateTimeCompany;
        this.modifiedByCompany = modifiedByCompany;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getTitleCompany() {
        return titleCompany;
    }

    public void setTitleCompany(String titleCompany) {
        this.titleCompany = titleCompany;
    }

    public String getAdd1Company() {
        return add1Company;
    }

    public void setAdd1Company(String add1Company) {
        this.add1Company = add1Company;
    }

    public String getAdd2Company() {
        return add2Company;
    }

    public void setAdd2Company(String add2Company) {
        this.add2Company = add2Company;
    }

    public String getCityCompany() {
        return cityCompany;
    }

    public void setCityCompany(String cityCompany) {
        this.cityCompany = cityCompany;
    }

    public String getStateCompany() {
        return stateCompany;
    }

    public void setStateCompany(String stateCompany) {
        this.stateCompany = stateCompany;
    }

    public String getZipCompany() {
        return zipCompany;
    }

    public void setZipCompany(String zipCompany) {
        this.zipCompany = zipCompany;
    }

    public String getPhoneCompany() {
        return phoneCompany;
    }

    public void setPhoneCompany(String phoneCompany) {
        this.phoneCompany = phoneCompany;
    }

    public String getLogoCompany() {
        return logoCompany;
    }

    public void setLogoCompany(String logoCompany) {
        this.logoCompany = logoCompany;
    }

    public String getCreateDateTimeCompany() {
        return createDateTimeCompany;
    }

    public void setCreateDateTimeCompany(String createDateTimeCompany) {
        this.createDateTimeCompany = createDateTimeCompany;
    }

    public String getCreateByCompany() {
        return createByCompany;
    }

    public void setCreateByCompany(String createByCompany) {
        this.createByCompany = createByCompany;
    }

    public String getModifiedDateTimeCompany() {
        return modifiedDateTimeCompany;
    }

    public void setModifiedDateTimeCompany(String modifiedDateTimeCompany) {
        this.modifiedDateTimeCompany = modifiedDateTimeCompany;
    }

    public String getModifiedByCompany() {
        return modifiedByCompany;
    }

    public void setModifiedByCompany(String modifiedByCompany) {
        this.modifiedByCompany = modifiedByCompany;
    }

    public String getRegionCompany() {
        return regionCompany;
    }

    public void setRegionCompany(String regionCompany) {
        this.regionCompany = regionCompany;
    }
}
