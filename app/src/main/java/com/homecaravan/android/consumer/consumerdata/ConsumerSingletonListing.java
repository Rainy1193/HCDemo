package com.homecaravan.android.consumer.consumerdata;

import java.util.ArrayList;

public class ConsumerSingletonListing {

    public static ConsumerSingletonListing singletonListing;
    public ArrayList<ConsumerListingFullStatus> arrListing = new ArrayList<>();

    public static ConsumerSingletonListing getInstance() {
        if (singletonListing == null) {
            singletonListing = new ConsumerSingletonListing();
        }
        return singletonListing;
    }

}
