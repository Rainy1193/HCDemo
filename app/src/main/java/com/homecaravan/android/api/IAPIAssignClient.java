package com.homecaravan.android.api;

import com.homecaravan.android.models.ResponseAssignClient;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by vankhoadesign on 7/3/16.
 */
public interface IAPIAssignClient {
    @FormUrlEncoded
    @POST("users_api/client_assign_agent")
    Call<ResponseAssignClient> AssignClient(@Field("TOKEN") String token, @Field("UID") String uid);
}
