package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageListingDetail {
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("image_thumb")
    @Expose
    private String imageThumb;
    @SerializedName("image_name")
    @Expose
    private String imageName;

    public String getImage() {
        return image;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public String getImageName() {
        return imageName;
    }

    @Override
    public String toString() {
        return "ImageListingDetail{" +
                "image='" + image + '\'' +
                ", imageThumb='" + imageThumb + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
