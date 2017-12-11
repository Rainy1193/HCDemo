package com.homecaravan.android.consumer.api;

import com.homecaravan.android.consumer.model.responseapi.ResponseAgentInfomation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Anh Dao on 11/3/2017.
 */

public interface UnlockAgentApi {
    @FormUrlEncoded
    @POST("users/set_agent")
    Call<ResponseAgentInfomation> setAgent(@Field("uid") String uid,
                                           @Field("identity") String identity,
                                           @Field("agent_id") String agentId);

    @FormUrlEncoded
    @POST("users/find_agent")
    Call<ResponseAgentInfomation> findAgent(@Field("code") String code);
}
