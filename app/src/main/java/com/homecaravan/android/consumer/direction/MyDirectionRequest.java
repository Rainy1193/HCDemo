package com.homecaravan.android.consumer.direction;

import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.network.DirectionConnection;
import com.akexorcist.googledirection.request.DirectionRequestParam;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyDirectionRequest {
    protected DirectionRequestParam param;

    public MyDirectionRequest(String apiKey, LatLng origin, LatLng destination) {
        param = new DirectionRequestParam().setApiKey(apiKey).setOrigin(origin).setDestination(destination);
    }

    public MyDirectionRequest waypoints(List<LatLng> waypoints) {
        param.setWaypoints(waypoints);
        return this;
    }

    public MyDirectionRequest transportMode(String transportMode) {
        param.setTransportMode(transportMode);
        return this;
    }

    public MyDirectionRequest language(String language) {
        param.setLanguage(language);
        return this;
    }

    public MyDirectionRequest unit(String unit) {
        param.setUnit(unit);
        return this;
    }

    public MyDirectionRequest avoid(String avoid) {
        String oldAvoid = param.getAvoid();
        if (oldAvoid != null && !oldAvoid.isEmpty()) {
            oldAvoid += "|";
        } else {
            oldAvoid = "";
        }
        oldAvoid += avoid;
        param.setAvoid(oldAvoid);
        return this;
    }

    public MyDirectionRequest transitMode(String transitMode) {
        String oldTransitMode = param.getTransitMode();
        if (oldTransitMode != null && !oldTransitMode.isEmpty()) {
            oldTransitMode += "|";
        } else {
            oldTransitMode = "";
        }
        oldTransitMode += transitMode;
        param.setTransitMode(oldTransitMode);
        return this;
    }

    public MyDirectionRequest alternativeRoute(boolean alternative) {
        param.setAlternatives(alternative);
        return this;
    }

    public MyDirectionRequest departureTime(String time) {
        param.setDepartureTime(time);
        return this;
    }

    public void execute(final MyDirectionCallback callback) {
        String waypointsStr = "";
        if (param.getWaypoints() != null) {
            waypointsStr = param.getWaypoints().get(0).latitude + "," + param.getWaypoints().get(0).longitude;
            for (LatLng latLng : param.getWaypoints()) {
                waypointsStr += "|" + latLng.latitude + "," + latLng.longitude;
            }
        }
        Call<Direction> direction = DirectionConnection.getInstance()
                .createService()
                .getDirection(param.getOrigin().latitude + "," + param.getOrigin().longitude,
                        param.getDestination().latitude + "," + param.getDestination().longitude,
                        waypointsStr,
                        param.getTransportMode(),
                        param.getDepartureTime(),
                        param.getLanguage(),
                        param.getUnit(),
                        param.getAvoid(),
                        param.getTransitMode(),
                        param.isAlternatives(),
                        param.getApiKey());

        direction.enqueue(new Callback<Direction>() {
            @Override
            public void onResponse(Call<Direction> call, Response<Direction> response) {
                if (callback != null) {
                    MyDirection myDirection = new MyDirection();
                    myDirection.setDirection(response.body());
                    myDirection.setStart(param.getOrigin());
                    callback.onDirectionSuccess(myDirection, new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Direction> call, Throwable t) {
                callback.onDirectionFailure(t);
            }
        });
    }
}
