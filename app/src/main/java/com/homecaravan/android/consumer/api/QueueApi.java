package com.homecaravan.android.consumer.api;

import com.homecaravan.android.consumer.model.responseapi.BaseResponse;
import com.homecaravan.android.consumer.model.responseapi.ResponseQueue;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface QueueApi {

    @FormUrlEncoded
    @POST("queue_api/add")
    Call<ResponseQueue> addQueue(@Field("lid") String lid);

    @FormUrlEncoded
    @POST("queue_api/remove")
    Call<BaseResponse> removeQueue(@Field("ids") String ids);

    @POST("queue_api/get_queue")
    Call<ResponseQueue> getQueue();
}
