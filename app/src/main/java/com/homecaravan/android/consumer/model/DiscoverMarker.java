package com.homecaravan.android.consumer.model;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterItem;
import com.homecaravan.android.consumer.model.listitem.ListingItem;

public class DiscoverMarker implements ClusterItem {
    private Marker mMarker;
    private StatusMarker status;
    private ListingItem mData;

    public DiscoverMarker(Marker mMarker, StatusMarker status, ListingItem mData) {
        this.mMarker = mMarker;
        this.status = status;
        this.mData = mData;
    }

    public Marker getMarker() {
        return mMarker;
    }

    public void setMarker(Marker mMarker) {
        this.mMarker = mMarker;
    }

    public StatusMarker getStatus() {
        return status;
    }

    public void setStatus(StatusMarker status) {
        this.status = status;
    }

    public ListingItem getData() {
        return mData;
    }

    public void setData(ListingItem mData) {
        this.mData = mData;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(Double.parseDouble(mData.getListing().getLat()),Double.parseDouble(mData.getListing().getLng()));
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return null;
    }
}
