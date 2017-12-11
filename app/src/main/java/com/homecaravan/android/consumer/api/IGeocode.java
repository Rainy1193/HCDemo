package com.homecaravan.android.consumer.api;


import com.homecaravan.android.consumer.model.responseapi.Geocode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The interface Geocode.
 * @author Dau Hung
 */
public interface IGeocode {
    /**
     * Gets location.
     *
     * @param address the address
     * @param b       the b
     * @return the location
     */
    @GET("https://maps.googleapis.com/maps/api/geocode/json")
    Call<Geocode> getLocation(@Query("address") String address, @Query("sensor") String b);

    /**
     * Gets address.
     *
     * @param latlng the latlng
     * @param b      the b
     * @return the address
     */
    @GET("https://maps.googleapis.com/maps/api/geocode/json")
    Call<Geocode> getAddress(@Query("latlng") String latlng, @Query("sensor") String b);
}
