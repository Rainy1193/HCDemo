package com.homecaravan.android.consumer.direction;

import com.google.android.gms.maps.model.LatLng;


public class MyDirectionDestinationRequest {
    String apiKey;
    LatLng origin;

    public MyDirectionDestinationRequest(String apiKey, LatLng origin) {
        this.apiKey = apiKey;
        this.origin = origin;
    }

    public MyDirectionRequest to(LatLng destination) {
        return new MyDirectionRequest(apiKey, origin, destination);
    }
}
