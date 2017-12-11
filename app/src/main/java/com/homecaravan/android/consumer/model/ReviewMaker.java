package com.homecaravan.android.consumer.model;

import com.google.android.gms.maps.model.Marker;


public class ReviewMaker {
    private Marker marker;
    private ConsumerListingSchedule listing;

    public ReviewMaker(Marker marker, ConsumerListingSchedule listing) {
        this.marker = marker;
        this.listing = listing;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public ConsumerListingSchedule getListing() {
        return listing;
    }

    public void setListing(ConsumerListingSchedule listing) {
        this.listing = listing;
    }
}
