package com.homecaravan.android.consumer.direction;

public class MyGoogleDirection {
    public static MyDirectionOriginRequest withServerKey(String apiKey) {
        return new MyDirectionOriginRequest(apiKey);
    }
}
