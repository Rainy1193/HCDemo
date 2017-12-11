package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListingImage {
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("image_thumb")
    @Expose
    private String imageThumb;
    @SerializedName("image_name")
    @Expose
    private String imageName;
    @SerializedName("images")
    @Expose
    private ArrayList<ImageListingDetail> arrImg;

    public String getImage() {
        return image;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public String getImageName() {
        return imageName;
    }

    public ArrayList<ImageListingDetail> getArrImg() {
        return arrImg;
    }

    @Override
    public String toString() {
        return "ListingImage{" +
                "image='" + image + '\'' +
                ", imageThumb='" + imageThumb + '\'' +
                ", imageName='" + imageName + '\'' +
                ", arrImg=" + arrImg +
                '}';
    }
}
