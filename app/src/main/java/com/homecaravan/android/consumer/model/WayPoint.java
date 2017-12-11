package com.homecaravan.android.consumer.model;


import com.google.android.gms.maps.model.LatLng;

public class WayPoint {
    private LatLng start;
    private LatLng end;

    public WayPoint(LatLng start, LatLng end) {
        this.start = start;
        this.end = end;
    }

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }

    public LatLng getEnd() {
        return end;
    }

    public void setEnd(LatLng end) {
        this.end = end;
    }
}
