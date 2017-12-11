package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 4/19/2016.
 */
public class ImageListing {
    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("ATTACHMENT_TYPE")
    @Expose
    private String type;
    @SerializedName("FILE_LOCATION")
    @Expose
    private String fileLocation;
    @SerializedName("POSITION")
    @Expose
    private String position;
    @SerializedName("CREATED_DATETIME")
    @Expose
    private String createdTime;
    @SerializedName("CREATED_BY")
    @Expose
    private String createdBy;
    @SerializedName("MODIFIED_DATETIME")
    @Expose
    private String modifiedTime;
    @SerializedName("MODIFIED_BY")
    @Expose
    private String modifiedBy;
    @SerializedName("LISTING_ID")
    @Expose
    private String listingId;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public String getPosition() {
        return position;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
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

    @Override
    public String toString() {
        return "ImageListing{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", position='" + position + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedTime='" + modifiedTime + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", listingId='" + listingId + '\'' +
                '}';
    }
}
