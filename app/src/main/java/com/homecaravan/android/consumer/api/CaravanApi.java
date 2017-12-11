package com.homecaravan.android.consumer.api;

import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;
import com.homecaravan.android.consumer.model.responseapi.ResponseParticipants;
import com.homecaravan.android.consumer.model.responseapi.ResponseSaveCaravan;
import com.homecaravan.android.consumer.model.responseapi.ResponseShowingAppt;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CaravanApi {

    @FormUrlEncoded
    @POST("caravan_api/create_from_queue")
    Call<ResponseCaravan> createFromQueue(@Field("queue") String queue, @Field("name") String name);

    @FormUrlEncoded
    @POST("caravan_api/add_participant")
    Call<ResponseParticipants> addParticipant(@Field("ID") String id,
                                              @Field("PARTNER[ID]") String idParticipant,
                                              @Field("PARTNER[EMAIL]") String email,
                                              @Field("PARTNER[PHONE]") String phone,
                                              @Field("PARTNER[ROLE]") String role,
                                              @Field("PARTNER[FIRST_NAME]") String firstName,
                                              @Field("PARTNER[LAST_NAME]") String lastName,
                                              @Field("PARTNER[COMMUNICATION_PREFERENCE]") String communication);

    @GET("caravan_api/get_recent_participant")
    Call<ResponseParticipants> getRecent();

    @FormUrlEncoded
    @POST("caravan_api/remove_participant")
    Call<ResponseParticipants> removeParticipant(@Field("ID") String id,
                                                 @Field("UIDS") String uids);

    @FormUrlEncoded
    @POST("caravan_api/gen_des_lst")
    Call<ResponseSaveCaravan> saveCaravan(@Field("ID") String id, @Field("D") String destination);

    @GET("caravan_api/detail/{id}")
    Call<ResponseCaravan> getCaravanDetail(@Path("id") String id);

    @FormUrlEncoded
    @POST("appointment_api_consumer/buyer_cancel_appt")
    Call<ResponseShowingAppt> buyerCancelAppointment(@Field("appt_id") String apptId);

}
