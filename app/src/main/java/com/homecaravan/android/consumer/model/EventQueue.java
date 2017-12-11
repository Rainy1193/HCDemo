package com.homecaravan.android.consumer.model;

import com.homecaravan.android.consumer.model.responseapi.Listing;
import com.homecaravan.android.consumer.model.responseapi.ListingFull;
import com.homecaravan.android.consumer.model.responseapi.ListingSearchMap;

public class EventQueue {
    public boolean addQueue;
    public String id;
    public Listing listing;
    public ListingFull listingFull;
    public ListingSearchMap listingSearchMap;

    public EventQueue(boolean addQueue, String id, Listing listing, ListingFull listingFull, ListingSearchMap listingSearchMap) {
        this.addQueue = addQueue;
        this.id = id;
        this.listing = listing;
        this.listingFull = listingFull;
        this.listingSearchMap = listingSearchMap;
    }
}
