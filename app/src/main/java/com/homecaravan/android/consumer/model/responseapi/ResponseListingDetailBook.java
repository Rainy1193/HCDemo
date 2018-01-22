package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homecaravan.android.models.ListingDetailData;

public class ResponseListingDetailBook extends BaseResponse {
    @Expose
    @SerializedName("data")
    private ListingDetailData data;

    public ListingDetailData getData() {
        return data;
    }
}
