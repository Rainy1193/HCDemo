package com.homecaravan.android.consumer.model;


import com.homecaravan.android.consumer.model.responseapi.Listing;

public class CurrentConsumerListing {
    public static CurrentConsumerListing currentListing;
    private Listing listingData;


    public static CurrentConsumerListing getInstance() {
        if (currentListing == null) {
            currentListing = new CurrentConsumerListing();
        }
        return currentListing;
    }

    public void setListingData(Listing listingData) {
        this.listingData = listingData;
    }

    public Listing getListingData() {
        return listingData;
    }
}
