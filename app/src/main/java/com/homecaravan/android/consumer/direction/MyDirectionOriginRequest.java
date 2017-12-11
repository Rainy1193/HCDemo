package com.homecaravan.android.consumer.direction;

import com.google.android.gms.maps.model.LatLng;

public class MyDirectionOriginRequest {
    private String apiKey;

    public MyDirectionOriginRequest(String apiKey) {
        this.apiKey = apiKey;
    }

    public MyDirectionDestinationRequest from(LatLng origin) {
        return new MyDirectionDestinationRequest(apiKey, origin);
    }
}
