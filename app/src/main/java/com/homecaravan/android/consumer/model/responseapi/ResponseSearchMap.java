package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseSearchMap extends BaseResponse {
    @SerializedName("data")
    @Expose
    private ResponseSearchMapData data;


    public class ResponseSearchMapData {
        @SerializedName("spots")
        @Expose
        ArrayList<ListingSearchMap> arrListing;
        @SerializedName("clusters")
        @Expose
        ArrayList<ClustersSearchMap> arrCluster;
        @SerializedName("listings")
        @Expose
        ArrayList<ListingListSearchMap> arrListingList;
        @SerializedName("total")
        @Expose
        int total;

        public ArrayList<ListingSearchMap> getArrListing() {
            return arrListing;
        }

        public int getTotal() {
            return total;
        }

        public ArrayList<ClustersSearchMap> getArrCluster() {
            return arrCluster;
        }

        public ArrayList<ListingListSearchMap> getArrListingList() {
            return arrListingList;
        }

        @Override
        public String toString() {
            return "ResponseSearchMapData{" +
                    "arrListing=" + arrListing +
                    '}';
        }
    }

    public ResponseSearchMapData getData() {
        return data;
    }


    @Override
    public String toString() {
        return "ResponseSearchMap{" +
                "data=" + data +
                '}';
    }
}
