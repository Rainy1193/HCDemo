package com.homecaravan.android.consumer.direction;

import com.akexorcist.googledirection.model.Direction;
import com.google.android.gms.maps.model.LatLng;


public class MyDirection {
    private Direction direction;
    private LatLng start;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }
}
