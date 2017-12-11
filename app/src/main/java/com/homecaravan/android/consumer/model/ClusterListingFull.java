package com.homecaravan.android.consumer.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.homecaravan.android.consumer.model.listitem.ListingFullItem;


public class ClusterListingFull implements ClusterItem {

    private ListingFullItem listingFullItem;

    public ClusterListingFull(ListingFullItem listingFullItem) {
        this.listingFullItem = listingFullItem;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(listingFullItem.getListing().getAddress().getLat(), listingFullItem.getListing().getAddress().getLng());
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
