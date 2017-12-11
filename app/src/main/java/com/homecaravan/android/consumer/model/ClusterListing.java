package com.homecaravan.android.consumer.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.homecaravan.android.consumer.model.responseapi.ListingSearchMap;

public class ClusterListing implements ClusterItem {

    private ListingSearchMap listingSearchMap;

    public ClusterListing(ListingSearchMap listingSearchMap) {
        this.listingSearchMap = listingSearchMap;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(Double.parseDouble(listingSearchMap.getLat()), Double.parseDouble(listingSearchMap.getLng()));
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
