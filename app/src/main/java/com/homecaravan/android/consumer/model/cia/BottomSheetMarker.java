package com.homecaravan.android.consumer.model.cia;

import com.google.android.gms.maps.model.Marker;
import com.homecaravan.android.consumer.model.responseapi.CaravanParticipants;
import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;

/**
 * Created by Anh Dao on 10/16/2017.
 */

public class BottomSheetMarker {
    private CaravanParticipants participants;
    private ResponseCaravan.CaravanDestinations destinations;
    private Marker marker;

    public BottomSheetMarker(){}

    public CaravanParticipants getParticipants() {
        return participants;
    }

    public void setParticipants(CaravanParticipants participants) {
        this.participants = participants;
    }

    public ResponseCaravan.CaravanDestinations getDestinations() {
        return destinations;
    }

    public void setDestinations(ResponseCaravan.CaravanDestinations destinations) {
        this.destinations = destinations;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
