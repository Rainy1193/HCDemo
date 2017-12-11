package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 4/19/2016.
 */
public class Listing extends ListingBase {
    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("LKEY")
    @Expose
    private String lkey;
    @SerializedName("USER_ID")
    @Expose
    private String userId;
    @SerializedName("STATUS")
    @Expose
    private String status;
    @SerializedName("AST")
    @Expose
    private String ast;
    @SerializedName("CREATED_DATETIME")
    @Expose
    private String createdTime;
    @SerializedName("CREATED_BY")
    @Expose
    private String createBy;
    @SerializedName("MODIFIED_DATETIME")
    @Expose
    private String modifiedTime;
    @SerializedName("MODIFIED_BY")
    @Expose
    private String modifiedBy;
    @SerializedName("LISTING_ID")
    @Expose
    private String listingId;
    @SerializedName("LISTING_TYPE")
    @Expose
    private String listingType;
    @SerializedName("SALE_TYPE")
    @Expose
    private String saleType;
    @SerializedName("PROPERTY_TYPE")
    @Expose
    private String propertyType;
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
    @SerializedName("LONGITUDE")
    @Expose
    private String longitude;
    @SerializedName("LATITUDE")
    @Expose
    private String latitude;
    @SerializedName("TIMEZONE")
    @Expose
    private String timeZone;
    @SerializedName("BEDROOMS")
    @Expose
    private String bedRooms;
    @SerializedName("BATHROOMS")
    @Expose
    private String bathRooms;
    @SerializedName("LIVING_SQUARE_FEET")
    @Expose
    private String livingSquareFeet;
    @SerializedName("LOT_SIZE")
    @Expose
    private String lotSize;
    @SerializedName("MEASURE_UNIT")
    @Expose
    private String measureUnit;
    @SerializedName("POOL")
    @Expose
    private String pool;
    @SerializedName("GARAGE")
    @Expose
    private String garage;
    @SerializedName("CONTACT_NAME")
    @Expose
    private String contactName;
    @SerializedName("MOBILE_PHONE")
    @Expose
    private String mobilePhone;
    @SerializedName("EMAIL")
    @Expose
    private String email;
    @SerializedName("PHOTO")
    @Expose
    private String photo;
    @SerializedName("DESCRIPTION")
    @Expose
    private String description;
    @SerializedName("PRICE")
    @Expose
    private String price;
    @SerializedName("PRICE_UNIT")
    @Expose
    private String priceUnit;
    @SerializedName("YEAR_BUILT")
    @Expose
    private String yearBuilt;
    @SerializedName("FOLLOWUP_TYPE")
    @Expose
    private String followupType;
    @SerializedName("REMINDER_TYPE")
    @Expose
    private String reminderType;
    @SerializedName("DISPLAY_TYPE")
    @Expose
    private String displayName;
    @SerializedName("NEXT_OPEN_HOUSE")
    @Expose
    private String nextOpenHouse;
    @SerializedName("FILE_LOCATION")
    @Expose
    private String fileLocation;
    @SerializedName("IN_FAVORITE")
    @Expose
    private String inFavorite;
    @SerializedName("THUMB")
    @Expose
    private String thumb;
    @SerializedName("IMG_NAME")
    @Expose
    private String imgName;
    @SerializedName("IMAGES")
    @Expose
    private ArrayList<ImageListing> arrImageListing;

    @SerializedName("DCP_ID")
    @Expose
    private String dcpId;
    @SerializedName("DCP_FULL_NAME")
    @Expose
    private String dcpFullName;

    @SerializedName("DCP_PHONE")
    @Expose
    private String dcpPhone;
    @SerializedName("DCP_EMAIL")
    @Expose
    private String dcpEmail;
    @SerializedName("DCP_PHOTO")
    @Expose
    private String dcpPhoto;
    @SerializedName("DCP_COMPANY_NAME")
    @Expose
    private String dcpCompanyName;
    @SerializedName("DCP_COMPANY_ADDRESS_1")
    @Expose
    private String dcpAdd1;
    @SerializedName("DCP_COMPANY_ADDRESS_2")
    @Expose
    private String dcpAdd2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLkey() {
        return lkey;
    }

    public void setLkey(String lkey) {
        this.lkey = lkey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAst() {
        return ast;
    }

    public void setAst(String ast) {
        this.ast = ast;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }



    public String getBedRooms() {
        return bedRooms;
    }

    public void setBedRooms(String bedRooms) {
        this.bedRooms = bedRooms;
    }

    public String getBathRooms() {
        return bathRooms;
    }

    public void setBathRooms(String bathRooms) {
        this.bathRooms = bathRooms;
    }

    public String getLivingSquareFeet() {
        return livingSquareFeet;
    }

    public void setLivingSquareFeet(String livingSquareFeet) {
        this.livingSquareFeet = livingSquareFeet;
    }

    public String getLotSize() {
        return lotSize;
    }

    public void setLotSize(String lotSize) {
        this.lotSize = lotSize;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(String yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public String getFollowupType() {
        return followupType;
    }

    public void setFollowupType(String followupType) {
        this.followupType = followupType;
    }

    public String getReminderType() {
        return reminderType;
    }

    public void setReminderType(String reminderType) {
        this.reminderType = reminderType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNextOpenHouse() {
        return nextOpenHouse;
    }

    public void setNextOpenHouse(String nextOpenHouse) {
        this.nextOpenHouse = nextOpenHouse;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getInFavorite() {
        return inFavorite;
    }

    public void setInFavorite(String inFavorite) {
        this.inFavorite = inFavorite;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public ArrayList<ImageListing> getArrImageListing() {
        return arrImageListing;
    }

    public void setArrImageListing(ArrayList<ImageListing> arrImageListing) {
        this.arrImageListing = arrImageListing;
    }

    public String getDcpId() {
        return dcpId;
    }

    public String getDcpFullName() {
        return dcpFullName;
    }

    public String getDcpEmail() {
        return dcpEmail;
    }

    public String getDcpPhoto() {
        return dcpPhoto;
    }

    public String getDcpCompanyName() {
        return dcpCompanyName;
    }

    public String getDcpAdd1() {
        return dcpAdd1;
    }

    public String getDcpAdd2() {
        return dcpAdd2;
    }

    public String getDcpPhone() {
        return dcpPhone;
    }
}
