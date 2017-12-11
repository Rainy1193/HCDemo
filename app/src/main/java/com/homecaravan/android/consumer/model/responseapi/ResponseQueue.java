package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseQueue extends BaseResponse {
    @SerializedName("data")
    @Expose
    private ResponseQueueData data;

    public ResponseQueueData getData() {
        return data;
    }

    public class ResponseQueueData {
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("data")
        @Expose
        private ArrayList<QueueData> data;

        public String getTotal() {
            return total;
        }

        public ArrayList<QueueData> getQueueData() {
            return data;
        }

        @Override
        public String toString() {
            return "ResponseQueueData{" +
                    "total='" + total + '\'' +
                    ", data=" + data +
                    '}';
        }
    }


    public class QueueData {
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("listing")
        @Expose
        private Listing listing;

        public Listing getListing() {
            return listing;
        }

        @Override
        public String toString() {
            return "ResponseQueueData{" +
                    "id='" + id + '\'' +
                    ", listing=" + listing +
                    '}';
        }
    }
}
