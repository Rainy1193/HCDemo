package com.homecaravan.android.consumer.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterItem;
import com.homecaravan.android.consumer.model.listitem.ListingFullItem;

public class DiscoverMarkerFull implements ClusterItem {
    private Marker mMarker;
    private StatusMarker status;
    private ListingFullItem mData;

    public DiscoverMarkerFull(Marker mMarker, StatusMarker status, ListingFullItem mData) {
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

    public ListingFullItem getData() {
        return mData;
    }

    public void setData(ListingFullItem mData) {
        this.mData = mData;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(mData.getListing().getAddress().getLat(), mData.getListing().getAddress().getLng());
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
