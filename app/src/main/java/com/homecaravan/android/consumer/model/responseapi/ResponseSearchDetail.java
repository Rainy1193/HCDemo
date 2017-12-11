package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSearchDetail extends BaseResponse {
    @SerializedName("data")
    @Expose
    private SearchDetail searchDetail;

    public SearchDetail getSearchDetail() {
        return searchDetail;
    }

    @Override
    public String toString() {
        return "ResponseSearchDetail{" +
                "searchDetail=" + searchDetail +
                '}';
    }
}
