
package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGetListingPrePage extends BaseResponse {
    @SerializedName("data")
    @Expose
    private ResponseListingSearchDetail listingData;

    public ResponseListingSearchDetail getListingData() {
        return listingData;
    }
}
