package com.homecaravan.android.consumer.model.cia;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 10/17/2017.
 */

public class ListingCIAMarker {
    private ResponseCaravan.CaravanDestinations destinations;
    private Marker marker;
    private boolean status;
    private int position;
    private ArrayList<ParticipantsMarker> participantsGoneThrough = new ArrayList<>();
    private LatLng latLng;
    private boolean youBeenLate;

    public ListingCIAMarker() {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<ParticipantsMarker> getParticipantsGoneThrough() {
        return participantsGoneThrough;
    }

    public void setParticipantsGoneThrough(ArrayList<ParticipantsMarker> participantsGoneThrough) {
        this.participantsGoneThrough = participantsGoneThrough;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public boolean isYouBeenLate() {
        return youBeenLate;
    }

    public void setYouBeenLate(boolean youBeenLate) {
        this.youBeenLate = youBeenLate;
    }
}
