package com.homecaravan.android.consumer.api;


import com.homecaravan.android.consumer.model.responseapi.ResponseNotification;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Anh Dao on 11/7/2017.
 */

public interface NotificationApi {

    @GET("notifications_api/get_notification")
    Call<ResponseNotification> getNotifications(@Query("type") String type,
                                                @Query("sdt") String sdt,
                                                @Query("edt") String edt,
                                                @Query("page_number") String pageNumber,
                                                @Query("per_page") String perPage);
}
