package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseAllSearch extends BaseResponse {
    @SerializedName("data")
    @Expose
    private ResponseAllSearchData data;

    public ResponseAllSearchData getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ResponseAllSearch{" +
                "data=" + data +
                '}';
    }

    public class ResponseAllSearchData {
        @SerializedName("data")
        @Expose
        private ArrayList<Search> arrSearch;
        @SerializedName("total")
        @Expose
        private String total;

        public ArrayList<Search> getArrSearch() {
            return arrSearch;
        }

        public String getTotal() {
            return total;
        }

        @Override
        public String toString() {
            return "ResponseAllSearchData{" +
                    "arrSearch=" + arrSearch +
                    '}';
        }
    }
}
