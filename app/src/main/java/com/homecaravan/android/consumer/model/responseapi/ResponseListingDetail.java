package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 10/26/2017.
 */

public class ResponseListingDetail extends BaseResponse {
    @SerializedName("data")
    @Expose
    private Listing listing;

    public Listing getListing() {
        return listing;
    }
}
