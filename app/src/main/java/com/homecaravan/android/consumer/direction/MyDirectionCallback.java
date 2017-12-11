package com.homecaravan.android.consumer.direction;


public interface MyDirectionCallback {
    void onDirectionSuccess(MyDirection direction, String rawBody);
    void onDirectionFailure(Throwable t);
}
