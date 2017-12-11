package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ClustersSearchMap {
    private boolean twoListing;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("doc_count")
    @Expose
    private int docCount;
    @SerializedName("location")
    @Expose
    private ClusterLocation location;
    @SerializedName("bounds")
    @Expose
    private ClusterBounds bounds;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getDocCount() {
        return docCount;
    }

    public void setDocCount(int docCount) {
        this.docCount = docCount;
    }

    public ClusterLocation getLocation() {
        return location;
    }

    public void setLocation(ClusterLocation location) {
        this.location = location;
    }

    public ClusterBounds getBounds() {
        return bounds;
    }

    public void setBounds(ClusterBounds bounds) {
        this.bounds = bounds;
    }

    public boolean isTwoListing() {
        return twoListing;
    }

    public void setTwoListing(boolean twoListing) {
        this.twoListing = twoListing;
    }

    public class ClusterLocation {
        @SerializedName("lat")
        @Expose
        private double lat;
        @SerializedName("lon")
        @Expose
        private double lon;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }

    public class ClusterBounds {
        @SerializedName("top_left")
        @Expose
        private ClusterLocation topLeft;
        @SerializedName("bottom_right")
        @Expose
        private ClusterLocation bottomRight;

        public ClusterLocation getTopLeft() {
            return topLeft;
        }

        public void setTopLeft(ClusterLocation topLeft) {
            this.topLeft = topLeft;
        }

        public ClusterLocation getBottomRight() {
            return bottomRight;
        }

        public void setBottomRight(ClusterLocation bottomRight) {
            this.bottomRight = bottomRight;
        }
    }
}
