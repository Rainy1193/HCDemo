package com.homecaravan.android.consumer.api;


import com.homecaravan.android.consumer.model.Predictions;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The interface Geocode.
 *
 * @author Dau Hung
 */
public interface ISearchPlace {

    @GET("autocomplete/json")
    Observable<Predictions> getPlace(@Query("key") String key, @Query("language") String language, @Query("input") String input);

}
