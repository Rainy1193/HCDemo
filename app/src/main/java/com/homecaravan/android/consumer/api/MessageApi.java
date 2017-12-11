package com.homecaravan.android.consumer.api;

import com.homecaravan.android.consumer.model.responseapi.BaseResponse;
import com.homecaravan.android.consumer.model.responseapi.ResponseMessageGetThreadId;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Anh Dao on 11/9/2017.
 */

public interface MessageApi {
    @FormUrlEncoded
    @POST("thread_api/allocate")
    Call<ResponseMessageGetThreadId> messageGetThreadId(@Field("appt_id") String apptId,
                                                        @Field("listing_id") String listingId,
                                                        @Field("caravan_id") String caravanId,
                                                        @Field("title") String title,
                                                        @Field("team_ids") String teamIds);

    @FormUrlEncoded
    @POST("thread_api/add_participants")
    Call<BaseResponse> messageAddParticipants(@Field("thread") String threadId,
                                              @Field("users") String UsersId);
}
