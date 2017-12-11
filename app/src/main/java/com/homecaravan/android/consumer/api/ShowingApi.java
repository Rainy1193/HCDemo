package com.homecaravan.android.consumer.api;

import com.homecaravan.android.consumer.model.responseapi.ResponseShowingAppt;
import com.homecaravan.android.consumer.model.responseapi.ResponseShowingCaravan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShowingApi {

    @GET("appointment_api_consumer/user_appointment_past")
    Call<ResponseShowingCaravan> getListShowingCaravanPast(@Query("e") String type);

    @GET("appointment_api_consumer/user_appointment")
    Call<ResponseShowingCaravan> getListShowingCaravan(@Query("start") String start, @Query("end") String end, @Query("e") String type);

    @GET("appointment_api_consumer/user_appointment")
    Call<ResponseShowingAppt> getListShowingAppointment(@Query("start") String start, @Query("end") String end, @Query("e") String type);
}
