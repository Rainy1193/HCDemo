package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageListingConsumer {
    @SerializedName("ID")
    @Expose
    private String id;
    @SerializedName("DEMO_IMAGE")
    @Expose
    private String demoImage;
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

    public String getDemoImage() {
        return demoImage;
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
}
