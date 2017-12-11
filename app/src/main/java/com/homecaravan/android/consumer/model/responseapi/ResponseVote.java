package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseVote extends BaseResponse {
    @Expose
    @SerializedName("data")
    ListingFull listingFull;

    public ListingFull getListingFull() {
        return listingFull;
    }
}
