package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ConsumerListingFull {

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

    @SerializedName("LASTEST_STATUS")
    @Expose
    private String lastestStatus;

    @SerializedName("DCP_ID")
    @Expose
    private String dcpId;
    @SerializedName("DCP_FULL_NAME")
    @Expose
    private String dcpFullName;
    @SerializedName("DCP_EMAIL")
    @Expose
    private String dcpEmail;
    @SerializedName("DCP_PHOTO")
    @Expose
    private String dcpPhoto;
    @SerializedName("DCP_PHONE")
    @Expose
    private String dcpPhone;
    @SerializedName("DCP_COMPANY_NAME")
    @Expose
    private String dcpCompanyName;
    @SerializedName("DCP_COMPANY_ADDRESS_1")
    @Expose
    private String dcpAdd1;
    @SerializedName("DCP_COMPANY_ADDRESS_2")
    @Expose
    private String dcpAdd2;

    @SerializedName("IMAGES")
    @Expose
    private ArrayList<ImageListingConsumer> arrImageListing;

    public String getId() {
        return id;
    }

    public String getLkey() {
        return lkey;
    }

    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public String getAst() {
        return ast;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public String getListingId() {
        return listingId;
    }

    public String getListingType() {
        return listingType;
    }

    public String getSaleType() {
        return saleType;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getAdd1() {
        return add1;
    }

    public String getAdd2() {
        return add2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getBedRooms() {
        return bedRooms;
    }

    public String getBathRooms() {
        return bathRooms;
    }

    public String getLivingSquareFeet() {
        return livingSquareFeet;
    }

    public String getLotSize() {
        return lotSize;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public String getPool() {
        return pool;
    }

    public String getGarage() {
        return garage;
    }

    public String getContactName() {
        return contactName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public String getYearBuilt() {
        return yearBuilt;
    }

    public String getFollowupType() {
        return followupType;
    }

    public String getReminderType() {
        return reminderType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public String getInFavorite() {
        return inFavorite;
    }

    public String getThumb() {
        return thumb;
    }

    public String getImgName() {
        return imgName;
    }

    public String getLastestStatus() {
        return lastestStatus;
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

    public String getDcpPhone() {
        return dcpPhone;
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

    public ArrayList<ImageListingConsumer> getArrImageListing() {
        return arrImageListing;
    }

    @Override
    public String toString() {
        return "ConsumerListingFull{" +
                "id='" + id + '\'' +
                ", lkey='" + lkey + '\'' +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", ast='" + ast + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", modifiedTime='" + modifiedTime + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", listingId='" + listingId + '\'' +
                ", listingType='" + listingType + '\'' +
                ", saleType='" + saleType + '\'' +
                ", propertyType='" + propertyType + '\'' +
                ", add1='" + add1 + '\'' +
                ", add2='" + add2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", bedRooms='" + bedRooms + '\'' +
                ", bathRooms='" + bathRooms + '\'' +
                ", livingSquareFeet='" + livingSquareFeet + '\'' +
                ", lotSize='" + lotSize + '\'' +
                ", measureUnit='" + measureUnit + '\'' +
                ", pool='" + pool + '\'' +
                ", garage='" + garage + '\'' +
                ", contactName='" + contactName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", priceUnit='" + priceUnit + '\'' +
                ", yearBuilt='" + yearBuilt + '\'' +
                ", followupType='" + followupType + '\'' +
                ", reminderType='" + reminderType + '\'' +
                ", displayName='" + displayName + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", inFavorite='" + inFavorite + '\'' +
                ", thumb='" + thumb + '\'' +
                ", imgName='" + imgName + '\'' +
                ", lastestStatus='" + lastestStatus + '\'' +
                ", dcpId='" + dcpId + '\'' +
                ", dcpFullName='" + dcpFullName + '\'' +
                ", dcpEmail='" + dcpEmail + '\'' +
                ", dcpPhoto='" + dcpPhoto + '\'' +
                ", dcpPhone='" + dcpPhone + '\'' +
                ", dcpCompanyName='" + dcpCompanyName + '\'' +
                ", dcpAdd1='" + dcpAdd1 + '\'' +
                ", dcpAdd2='" + dcpAdd2 + '\'' +
                ", arrImageListing=" + arrImageListing +
                '}';
    }
}
