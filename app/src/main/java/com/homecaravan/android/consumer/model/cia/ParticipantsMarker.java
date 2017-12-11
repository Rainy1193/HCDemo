package com.homecaravan.android.consumer.model.cia;

import com.google.android.gms.maps.model.Marker;
import com.homecaravan.android.consumer.model.responseapi.CaravanParticipants;

/**
 * Created by Anh Dao on 10/10/2017.
 */

public class ParticipantsMarker {

    private CaravanParticipants participants;
    private Marker marker;
    private boolean hasOnMap;

    public ParticipantsMarker() {
    }

    public CaravanParticipants getParticipants() {
        return participants;
    }

    public void setParticipants(CaravanParticipants participants) {
        this.participants = participants;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public boolean isHasOnMap() {
        return hasOnMap;
    }

    public void setHasOnMap(boolean hasOnMap) {
        this.hasOnMap = hasOnMap;
    }
}
