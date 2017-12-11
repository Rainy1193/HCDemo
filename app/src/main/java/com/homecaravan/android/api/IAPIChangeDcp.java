package com.homecaravan.android.api;

import com.homecaravan.android.models.ResponseListMember;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by RAINY on 6/29/2016.
 */
public interface IAPIChangeDcp {
    @FormUrlEncoded
    @POST("listing_api/update_change_receptionist")
    Call<ResponseListMember> changeStatus(@Field("LISTING_ID") String listingId, @Field("TOKEN") String token,
                                          @Field("USER_ID") String userId, @Field("OLD_RECEPTIONIST") String oldReceptionist);
}
